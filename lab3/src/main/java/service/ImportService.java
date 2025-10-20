package service;

import com.opencsv.exceptions.CsvException;
import exception.InvalidDataException;
import model.*;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.Path;

public class ImportService {

    public static ImportSummary importFromCsv(String resourcePath) throws IOException {
        ImportSummary importSummary = new ImportSummary();

        Path path = Path.of(resourcePath);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + resourcePath);
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int lineCount = 0;
            String header = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                lineCount++;
                try {
                    processLine(line, lineCount, importSummary);
                } catch (Exception e) {
                    throw new IOException("Error processing line " + lineCount, e);
                }
            }
        }

        return importSummary;
    }


private static void processLine(String line, int lineCount, ImportSummary importSummary) throws Exception {
        
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

            EmployeeService.AddNewEmployee(corp, newEmployee);
            importSummary.addNewImported();

        } catch (Exception e) {
                importSummary.addNewError(lineCount, e);
        }

    }
}



