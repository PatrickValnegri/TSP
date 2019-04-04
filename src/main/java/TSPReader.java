import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TSPReader {

    private static int id = 0;

    public ArrayList<City> readFile(InputStream input) {
        if (input != null) {
            return parseTSP(input);
        }
        return null;
    }

    private ArrayList<City> parseTSP(InputStream input) {
        ArrayList<City> cities = new ArrayList<City>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            while (line != null) {
                //TODO togliere spazi prima di dividere la riga
                String[] parts = line.trim().split(" ");

                if (parts[0].contains("EOF"))
                    return cities;

                if (parts.length == 3 && parts[0].matches("^[0-9]*$")) {
                    //System.out.println("City number: " + parts[0] + " lat: " + parts[1] + " long: " + parts[2]);
                    cities.add(new City(id, Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
                    id++;
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
