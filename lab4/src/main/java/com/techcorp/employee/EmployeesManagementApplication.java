package com.techcorp.employee;

import com.opencsv.exceptions.CsvException;
import com.techcorp.employee.model.Corporation;
import com.techcorp.employee.model.Employee;
import com.techcorp.employee.model.ImportSummary;
import com.techcorp.employee.model.Position;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import com.techcorp.employee.service.ApiService;
import com.techcorp.employee.service.EmployeeService;
import com.techcorp.employee.service.ImportService;

import java.io.IOException;
import java.util.List;

@ImportResource({
        "classpath:employees-beans.xml",
        "classpath:corporation-beans.xml"
})
@SpringBootApplication
public class EmployeesManagementApplication implements CommandLineRunner {
    private final EmployeeService employeeService;
    private final ApiService apiService;
    private final ImportService importService;
    private final List<Employee> predefinedEmployees;
    private final List<Corporation> predefinedCorporations;
    @Value("${app.import.csv-file}")
    private String csvfile;

    @Value("${app.api.url}")
    private String url;

    public EmployeesManagementApplication(
            EmployeeService employeeService,
            ApiService apiService,
            ImportService importService,
            List<Employee> predefinedEmployees,
            List<Corporation> predefinedCorporations) {
        this.employeeService = employeeService;
        this.apiService = apiService;
        this.importService = importService;
        this.predefinedEmployees = predefinedEmployees;
        this.predefinedCorporations = predefinedCorporations;
    }


    public static void main(String[] args) {
        SpringApplication.run(EmployeesManagementApplication.class, args);
    }
    @Override
    public void run(String... args) throws IOException, CsvException, InterruptedException {
        System.out.println("\n========================================");
        System.out.println("Starting Employees Management Application");
        System.out.println("========================================\n");

        // Add books from XML configuration
        System.out.println("\nLoading predefined employees from XML...");
        for (Employee employee : predefinedEmployees) {
            employeeService.addNewEmployee(predefinedCorporations.get(0), employee);
        }

        System.out.println("\n========================================");
        System.out.println("Importing from CSV");
        System.out.println("========================================\n");

        ImportSummary summaryCSV = importService.importFromCsv(csvfile);
        System.out.println("Successfully imported from CSV: " + " " + String.valueOf(summaryCSV.getImportedSuccessfully()));
        System.out.println("Errors in importing from CSV: " + "\n" + summaryCSV.getErrors());

        System.out.println("\n========================================");
        System.out.println("Importing from API");
        System.out.println("========================================\n");
        ImportSummary summaryAPI = apiService.fetchEmployeesFromAPI(url);
        System.out.println("Successfully imported from API: " + " " + String.valueOf(summaryAPI.getImportedSuccessfully()));


        System.out.println("\n========================================");
        System.out.println("Application finished successfully!");
        System.out.println("========================================\n");
    }

    }
