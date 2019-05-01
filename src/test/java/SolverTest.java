import TSP.models.Solver;
import org.junit.Test;

public class SolverTest {

    @Test(timeout = 181000)
    public void ch130() {
        Solver solver = new Solver();
        solver.solve("ch130", 6110, 1556192027286L);
    }

    @Test(timeout = 181000)
    public void d198() {
        Solver solver = new Solver();
        solver.solve("d198", 15780, 1556192564752L);
    }

    @Test(timeout = 181000)
    public void eil76() {
        Solver solver = new Solver();
        solver.solve("eil76", 538, 1556191623069L);
    }

    @Test(timeout = 181000)
    public void fl1577() {
        Solver solver = new Solver();
        solver.solve("fl1577",22249, 1556174677206L);
    }

    @Test(timeout = 181000)
    public void kroA100() {
        Solver solver = new Solver();
        solver.solve("kroA100",21282, 1554294766625L);
    }

    @Test(timeout = 181000)
    public void lin318() {
        Solver solver = new Solver();
        solver.solve("lin318",42029, 1554296009136L);
    }

    @Test(timeout = 181000)
    public void pcb442() {
        Solver solver = new Solver();
        solver.solve("pcb442",50778, 1556182944483L);
    }

    @Test(timeout = 181000)
    public void pr439() {
        Solver solver = new Solver();
        solver.solve("pr439",107217, 1554297939294L);
    }

    @Test(timeout = 181000)
    public void rat783() {
        Solver solver = new Solver();
        solver.solve("rat783",8806, 1556645734524L);
    }

    @Test(timeout = 181000)
    public void u1060() {
        Solver solver = new Solver();
        solver.solve("u1060",224094, 1556223989423L);
    }

}

