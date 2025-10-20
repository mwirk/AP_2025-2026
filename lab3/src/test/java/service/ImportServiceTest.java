package service;

import com.opencsv.exceptions.CsvException;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImportServiceTest {
    @BeforeEach
    void tearUp(){
        World.getCorporationList().clear();
    }
    @Test
    void testImportFromCsv_Valid(@TempDir Path tempDir) throws IOException, CsvException {
        String csvContent = "FirstName;LastName;Mail;Corporation;Position;Salary\n" +
                "Tomasz;Pracownik;tomasz@gmail.com;wodociągi;Intern;3000\n" +
                "Ryszard;Pracownik;ryszard@gmail.com;wodociągi;Intern;2000\n";


        Path csvFile = tempDir.resolve("employees.csv");
        Files.write(csvFile, csvContent.getBytes());

        ImportSummary summaryCSV = ImportService.importFromCsv(csvFile.toString());

        List<Employee> employees = World.getCorporationList().get(0).getEmployeeList();
        assertAll(
                () -> assertEquals(2, employees.size()),
                () -> assertEquals("Tomasz", employees.get(0).getName()),
                () -> assertEquals(Position.Intern, employees.get(0).getPosition())
        );
    }

    @Test
    void testImportFromCsv_Invalid_StringInSalary(@TempDir Path tempDir) throws IOException, CsvException {
        String csvContent = "FirstName;LastName;Mail;Corporation;Position;Salary\n" +
                "Tomasz;Pracownik;tomasz@gmail.com;wodociągi;Intern;gg\n" +
                "Ryszard;Pracownik;ryszard@gmail.com;wodociągi;Intern;2000\n";


        Path csvFile = tempDir.resolve("employees.csv");
        Files.write(csvFile, csvContent.getBytes());

        ImportSummary summaryCSV = ImportService.importFromCsv(csvFile.toString());

        List<Employee> employees = World.getCorporationList().get(0).getEmployeeList();
        String expectedPart = "Error in line: 1, Reason: java.lang.NumberFormatException";
        assertTrue(summaryCSV.getErrors().get(0).contains(expectedPart));



    }
    @Test
    void testImportFromCsv_Invalid_NegativeSalary(@TempDir Path tempDir) throws IOException, CsvException {
        String csvContent = "FirstName;LastName;Mail;Corporation;Position;Salary\n" +
                "Tomasz;Pracownik;tomasz@gmail.com;wodociągi;Intern;-2000\n" +
                "Ryszard;Pracownik;ryszard@gmail.com;wodociągi;Intern;2000\n";


        Path csvFile = tempDir.resolve("employees.csv");
        Files.write(csvFile, csvContent.getBytes());

        ImportSummary summaryCSV = ImportService.importFromCsv(csvFile.toString());
        String expectedPart = "Error in line: 1, Reason: exception.InvalidDataException: Salary must be positive: -2000.0";
        assertTrue(summaryCSV.getErrors().get(0).contains(expectedPart));




    }
    @Test
    void testImportFromCsv_IvalidPosition_ContinuationOfImporting(@TempDir Path tempDir) throws IOException, CsvException {
        String csvContent = "FirstName;LastName;Mail;Corporation;Position;Salary\n" +
                "Tomasz;Pracownik;tomasz@gmail.com;wodociągi;Internnkn;3000\n" +
                "Ryszard;Pracownik;ryszard@gmail.com;wodociągi;Intern;2000\n";


        Path csvFile = tempDir.resolve("employees.csv");
        Files.write(csvFile, csvContent.getBytes());

        ImportSummary summaryCSV = ImportService.importFromCsv(csvFile.toString());
        String expectedPart = "Error in line: 1, Reason: exception.InvalidDataException: Invalid position: Internnkn";

        List<Employee> employees = World.getCorporationList().get(0).getEmployeeList();
        assertAll(
                () -> assertEquals(1, employees.size()),
                () -> assertEquals("Ryszard", employees.get(0).getName()),
                () -> assertTrue(summaryCSV.getErrors().get(0).contains(expectedPart))
        );

    }


}
