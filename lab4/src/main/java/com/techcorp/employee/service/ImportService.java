package com.techcorp.employee.service;

import com.opencsv.exceptions.CsvException;
import com.techcorp.employee.exception.InvalidDataException;
import com.techcorp.employee.model.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;

@Service
public class ImportService {

    private final EmployeeService employeeService;


    public ImportService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public ImportSummary importFromCsv(String resourcePath) throws IOException, CsvException {
        ImportSummary importSummary = new ImportSummary();

        InputStream inputStream = ImportService.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
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

            Employee newEmployee = new Employee(columns[0], columns[1], columns[2], position, salary);

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
