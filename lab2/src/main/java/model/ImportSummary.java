package model;

import java.util.ArrayList;
import java.util.List;

public class ImportSummary {
    private int importedSuccessfully = 0;
    private List<String> errors = new ArrayList<>();

    public int getImportedSuccessfully() {
        return importedSuccessfully;
    }

    public List<String> getErrors() {
        return errors;
    }
    public void setImportedSuccessfully(int importedSuccessfully){
        this.importedSuccessfully = importedSuccessfully;
    }
    public void addNewImported(){
        setImportedSuccessfully(getImportedSuccessfully() + 1);
    }
    public void addNewError(int numberLine, Exception exception){
        errors.add("Error in line: " +  String.valueOf(numberLine) + ", " + "Reason: " + exception + "\n");
    }
}
