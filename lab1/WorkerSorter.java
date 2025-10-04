
import java.util.Comparator;

public class WorkerSorter implements Comparator<Worker> {
    @Override
    public int compare(Worker i, Worker j) {
        int surnameComparison = i.getSurname().compareTo(j.getSurname());
        if (surnameComparison != 0) {
            return surnameComparison;
        }
        return i.getName().compareTo(j.getName());
    }
}