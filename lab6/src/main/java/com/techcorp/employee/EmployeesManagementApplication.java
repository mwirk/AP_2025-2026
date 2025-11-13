package com.techcorp.employee;

import com.opencsv.exceptions.CsvException;
import com.techcorp.employee.controller.FileUploadController;
import com.techcorp.employee.model.Corporation;
import com.techcorp.employee.model.Employee;
import com.techcorp.employee.model.ImportSummary;
import com.techcorp.employee.model.World;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import com.techcorp.employee.service.ApiService;
import com.techcorp.employee.service.EmployeeService;
import com.techcorp.employee.service.ImportService;
import com.techcorp.employee.service.FileStorageService;
import java.io.IOException;
import java.util.List;

@ImportResource({
        "classpath:employees-beans.xml",
        "classpath:corporation-beans.xml"
})
@SpringBootApplication
public class EmployeesManagementApplication{
    private final EmployeeService employeeService;
    private final ApiService apiService;
    private final ImportService importService;
    private final FileStorageService fileStorageService;
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
            FileStorageService fileStorageService,
            List<Employee> predefinedEmployees,
            List<Corporation> predefinedCorporations) {
        this.employeeService = employeeService;
        this.apiService = apiService;
        this.importService = importService;
        this.fileStorageService = fileStorageService;
        this.predefinedEmployees = predefinedEmployees;
        this.predefinedCorporations = predefinedCorporations;
    }


    public static void main(String[] args) {
        SpringApplication.run(EmployeesManagementApplication.class, args);

    }


    }
//curl -X GET http://localhost:8080/api/employees
//curl -X GET "http://localhost:8080/api/employees?company=TechCorp"
//curl -X GET http://localhost:8080/api/employees/jan.kowalski@techcorp.com
//curl -X POST http://localhost:8080/api/employees \
//        -H "Content-Type: application/json" \
//        -d '{
//        "name": "Jan",
//        "surname": "Kowalski",
//        "mail": "jan.kowalski@techcorp.com",
//        "company": "TechCorp",
//        "position": "Programmer",
//        "salary": 8000,
//        "status": "ACTIVE"
//        }'

//curl -X PUT http://localhost:8080/api/employees/jan.kowalski@techcorp.com \
//        -H "Content-Type: application/json" \
//        -d '{
//        "name": "Jan",
//        "surname": "Nowak",
//        "mail": "jan.nowak@techcorp.com",
//        "company": "TechCorp",
//        "position": "Programmer",
//        "salary": 10000,
//        "status": "ACTIVE"
//        }'

//curl -X DELETE http://localhost:8080/api/employees/jan.kowalski@techcorp.com

//curl -X PATCH http://localhost:8080/api/employees/jan.kowalski@techcorp.com/status \
//        -H "Content-Type: application/json" \
//        -d '"TERMINATED"'


//curl -X GET http://localhost:8080/api/employees/status/ACTIVE






//curl -v -X POST http://localhost:8080/api/files/import/csv   -F "file=@src/main/resources/data/file.csv;type=text/csv"
//curl http://localhost:8080/api/files/export/csv \
//  --output employees_export.csv
//curl -X POST http://localhost:8080/api/files/documents/jan.kowalski@techcorp.com   -F "file=@src/main/resources/data/contract.pdf"   -F "type=CONTRACT"
// http://localhost:8080/api/files/documents/jan.kowalski@techcorp.com



//curl -X POST http://localhost:8080/api/files/photos/jan@example.com \
//        -F "file=@src/main/resources/data/photo.jpg"

//curl http://localhost:8080/api/files/photos/jan.kowalski@techcorp.com   --output photo.jpg