package com.techcorp.employee.service;

import com.opencsv.exceptions.CsvException;
import com.techcorp.employee.exception.InvalidDataException;
import com.techcorp.employee.model.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class ImportService {

    private final EmployeeService employeeService;
    private final FileStorageService fileStorageService;


    public ImportService(EmployeeService employeeService, FileStorageService fileStorageService) {
        this.employeeService = employeeService;
        this.fileStorageService = fileStorageService;
    }

    public ImportSummary importFromCsv(String resourcePath) throws IOException, CsvException {
        ImportSummary importSummary = new ImportSummary();

        Path filePath = fileStorageService.loadFile(resourcePath);

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int lineCount = 0;
            String header = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                lineCount++;
                processLine(line, lineCount, importSummary);
            }
        }

        return importSummary;
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

            Employee newEmployee = new Employee(columns[0], columns[1], columns[2], position, salary, Status.ACTIVE);

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
