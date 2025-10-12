import com.opencsv.exceptions.CsvException;
import model.*;
import service.ApiService;
import service.EmployeeService;
import service.ImportService;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, CsvException, InterruptedException {



        Employee tadeusz = new Employee("tadeusz","pracownik", "mail", Position.Programmer, 2000.0f);
        Corporation corp = new Corporation("corp");
        EmployeeService.AddNewEmployee(corp, tadeusz);
        System.out.println(EmployeeService.getCompanyStatistics(corp).toString());



        ImportSummary summaryCSV = ImportService.importFromCsv("data/file.csv");
        System.out.println("Successfully imported from CSV: " + " " + String.valueOf(summaryCSV.getImportedSuccessfully()));
        System.out.println("Errors in importing from CSV: " + "\n" + summaryCSV.getErrors());


        ImportSummary summaryAPI = ApiService.fetchEmployeesFromAPI();
        System.out.println("Successfully imported from API: " + " " + String.valueOf(summaryAPI.getImportedSuccessfully()));
    }
}