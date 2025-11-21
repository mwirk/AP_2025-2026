package com.techcorp.employee.service;

import com.opencsv.exceptions.CsvException;
import com.techcorp.employee.dao.JdbcEmployeeDao;
import com.techcorp.employee.exception.InvalidDataException;
import com.techcorp.employee.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class ImportService {
    private final JdbcEmployeeDao employeeDao;

    private final EmployeeService employeeService;
    private final FileStorageService fileStorageService;


    public ImportService(JdbcEmployeeDao employeeDao, EmployeeService employeeService, FileStorageService fileStorageService) {
        this.employeeService = employeeService;
        this.fileStorageService = fileStorageService;
        this.employeeDao = employeeDao;
    }


    @Transactional
    public ImportSummary importFromCsv(String resourcePath) throws IOException, CsvException {

        ImportSummary importSummary = new ImportSummary();


        employeeDao.deleteAll();

        Path filePath = fileStorageService.loadFile(resourcePath);

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {

            String header = reader.readLine();

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                try {
                    Employee employee = parseEmployee(line);

                    employeeDao.save(employee);
                    importSummary.addNewImported();

                } catch (Exception ex) {
                    importSummary.addNewError(
                            lineNumber, ex
                    );
                }
            }
        }

        return importSummary;
    }
    private Employee parseEmployee(String csvLine) {

        String[] parts = csvLine.split(",");

        if (parts.length < 7) {
            throw new IllegalArgumentException("Invalid CSV format");
        }

        String name = parts[0].trim();
        String surname = parts[1].trim();
        String mail = parts[2].trim();
        String corporation = parts[3].trim();
        Position position = Position.valueOf(parts[4].trim());
        Float salary = Float.parseFloat(parts[5].trim());
        Status status = Status.valueOf(parts[6].trim());
        String photo = parts.length > 7 ? parts[7].trim() : "";

        return new Employee(
                name,
                surname,
                mail,
                corporation,
                position,
                salary,
                status,
                photo
        );
    }



    private void processLine(String line, int lineCount, ImportSummary importSummary) {
        String[] columns = line.split(";");
        try {
            String positionStr = columns[4];
            Position position = Arrays.stream(Position.values())
                    .filter(p -> p.name().equalsIgnoreCase(positionStr))
                    .findFirst()
                    .orElseThrow(() -> new InvalidDataException("Invalid position: " + positionStr));

            float salary = Float.parseFloat(columns[5]);
            if (salary <= 0.0f) {
                throw new InvalidDataException("Salary must be positive: " + salary);
            }

            Employee newEmployee = new Employee(columns[0], columns[1], columns[2], "",position, salary, Status.ACTIVE, "");

            String companyName = columns[3];
            Corporation corp = World.getCorporationList()
                    .stream()
                    .filter(c -> c.getName().equalsIgnoreCase(companyName))
                    .findFirst()
                    .orElseGet(() -> new Corporation(companyName));

            employeeService.addNewEmployee(corp, newEmployee);
            importSummary.addNewImported();

        } catch (Exception e) {
            importSummary.addNewError(lineCount, e);
        }
    }
}
