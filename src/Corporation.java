
import java.util.ArrayList;
import java.util.List;

public class Corporation {
    private String name;
    private List<Worker> workerList;

    public Corporation(String name, List<Worker> workerList){
        this.name = name;
        this.workerList = workerList;
    }
    public Corporation(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public List<Worker> getWorkerList(){
        return this.workerList;
    }
    public void setWorkerList(ArrayList<Worker> newList){
        this.workerList = newList;
    }
}