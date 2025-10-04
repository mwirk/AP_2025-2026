import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        Corporation wodociagi = new Corporation("Wodociągi miejskie", new ArrayList<Worker>());
        Worker tadeusz = new Worker("Tadeusz", "Norek", "norek@wp.pl", Position.Intern);
        AddNewWorker.apply(wodociagi, tadeusz);
        System.out.println(GetGrouppedListOfWorkers.apply(wodociagi).get(Position.Intern));
    }
}