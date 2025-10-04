import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Optional;



class AddNewWorker {

    public static void apply(Corporation corporation, Worker newWorker){
        List<Worker> list = corporation.getWorkerList();
        if (list.stream().anyMatch(worker -> worker.equalsMail(newWorker))) {
            throw new IllegalArgumentException("Worker with this mail already exists");
        }else {
            list.add(newWorker);
            newWorker.setCorporation(corporation.getName());
        }
    }
}

class GetListOfWorkers{

    public static List<String> apply(Corporation corporation) {
        return corporation.getWorkerList().stream()
                .map(worker -> "Name: " + worker.getName() +
                        ", Surname: " + worker.getSurname() +
                        ", Mail: " + worker.getMail() +
                        ", Corporation: " + worker.getCorporation()).collect(Collectors.toList());

    }
}

class FindWorker {

    public static List<Worker> apply(Corporation corporation, Worker workerToFind) {
        return corporation.getWorkerList().stream().filter(worker -> worker.equals(workerToFind)).collect(Collectors.toList());

    }
}


class SortWorkers  {

    public static void apply(Corporation corporation) {

        Collections.sort(corporation.getWorkerList(), new WorkerSorter());

    }
}

class GetGrouppedListOfWorkers  {

    public static Map<Position, List<Worker>> apply(Corporation corporation) {
        Map<Position, List<Worker>> grouped = corporation.getWorkerList().stream()
                .collect(Collectors.groupingBy(Worker::getPosition));

//        grouped.forEach((position, workers) -> {
//            System.out.println("Position: " + position);
//            workers.forEach(worker ->
//                    System.out.println("  - " + worker.getName() + " " + worker.getSurname()));
//        });
        return grouped;
    }
}

class GetAmountOfEachPosition  {

    public static Map<Position, List<Worker>> apply(Corporation corporation) {
        Map<Position, List<Worker>> grouped = corporation.getWorkerList().stream()
                .collect(Collectors.groupingBy(Worker::getPosition));

//        grouped.forEach((position, workers) -> {
//            System.out.println("Position: " + position + ", " + "Amount: " + workers.size());
//
//        });
        return grouped;

    }
}

class GetAverageSalary {

    public static Float apply(Corporation corporation) {
        List<Float> salaries = corporation.getWorkerList().stream()
                .map(Worker::getSalary)
                .toList();
        Float sum = salaries.stream()
                .reduce(0.0f, (a, b) -> a + b);
        return sum / corporation.getWorkerList().size();

    }
}


class FindMostExpensiveWorker {
    public static Worker apply(Corporation corporation) {
        Optional<Worker> maxWorker = corporation.getWorkerList().stream()
                .max(Comparator.comparing(Worker::getSalary));

        return maxWorker.orElse(null);
    }
}

