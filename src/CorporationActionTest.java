
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class CorporationActionTest {

    Corporation corporation = new Corporation("Wodociągi miejskie", new ArrayList<Worker>());

    @Test
    public void testCorp_AddProduct_Valid() {
        corporation.setWorkerList(new ArrayList<Worker>());
        Worker tadeusz = new Worker("Tadeusz", "Norek", "norek@wp.pl", Position.Intern);
        AddNewWorker.apply(corporation, tadeusz);
        assertEquals(corporation.getWorkerList().getFirst().getName(), tadeusz.getName());
    }

    @Test
    public void testCorp_FindWorker_Valid() {
        corporation.setWorkerList(new ArrayList<Worker>());
        Worker tadeusz = new Worker("Tadeusz", "Norek", "norek@wp.pl", Position.Intern);
        AddNewWorker.apply(corporation, tadeusz);
        assertEquals(FindWorker.apply(corporation, tadeusz).getFirst().getName(), tadeusz.getName());
    }

    @Test
    public void testCorp_SortWorker_Valid() {
        corporation.setWorkerList(new ArrayList<Worker>());
        Worker tadeusz = new Worker("Tadeusz", "Norek", "norek@wp.pl", Position.Intern);
        AddNewWorker.apply(corporation, tadeusz);
        Worker tadeusz2 = new Worker("Tadeusz", "Aorek", "norekk@wp.pl", Position.Intern);
        AddNewWorker.apply(corporation, tadeusz2);
        SortWorkers.apply(corporation);
        assertEquals(corporation.getWorkerList().getFirst().getSurname(), tadeusz2.getSurname());
    }

    @Test
    public void testCorp_GetAverageSalary_Valid() {
        corporation.setWorkerList(new ArrayList<Worker>());
        Worker tadeusz = new Worker("Tadeusz", "Norek", "norek@wp.pl", Position.Intern);
        AddNewWorker.apply(corporation, tadeusz);
        Worker amadeusz = new Worker("Amadeusz", "Prezes", "prezesbogacz@wp.pl", Position.CEO);
        AddNewWorker.apply(corporation, amadeusz);
        Float salary = GetAverageSalary.apply(corporation);
        assertEquals(14000.0, salary.doubleValue(), 0.0001);

    }

    @Test
    public void testCorp_GetMostExpensiveWorker_Valid() {
        corporation.setWorkerList(new ArrayList<Worker>());
        Worker tadeusz = new Worker("Tadeusz", "Norek", "norek@wp.pl", Position.Intern);
        AddNewWorker.apply(corporation, tadeusz);
        Worker amadeusz = new Worker("Amadeusz", "Prezes", "prezesbogacz@wp.pl", Position.CEO);
        AddNewWorker.apply(corporation, amadeusz);
        assertEquals(amadeusz, FindMostExpensiveWorker.apply(corporation));


    }
}
