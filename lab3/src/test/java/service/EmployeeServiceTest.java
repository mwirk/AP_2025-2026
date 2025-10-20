package service;


import model.CompanyStatistics;
import model.Corporation;
import model.Employee;
import model.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    private Corporation corporation;

    @BeforeEach
    void setUp() {
        corporation = new Corporation("Corp");
    }
    @AfterEach
    void tearUp(){
        corporation = null;

    }

    @Test
    public void testCorp_AddProduct_Valid() {
        Employee employee = new Employee("Tadeusz", "Norek", "norek@wp.pl", Position.Intern, 3000f);
        EmployeeService.AddNewEmployee(corporation, employee);
        assertEquals(corporation.getEmployeeList().get(0).getMail(), employee.getMail());
    }
    @Test
    public void testCorp_AddProduct_Invalid_SameMail() {
        Employee employee = new Employee("Tadeusz", "Norek", "norek@wp.pl", Position.Intern, 3000f);
        EmployeeService.AddNewEmployee(corporation, employee);
        Employee employee2 = new Employee("Tadeusz", "Borek", "norek@wp.pl", Position.Intern, 3000f);
        Exception exception = assertThrows(
              IllegalArgumentException.class,
              () -> EmployeeService.AddNewEmployee(corporation, employee2));
        assertEquals("model.Employee with this mail already exists", exception.getMessage());
    }

    @Test
    public void testCorp_AddProduct_Invalid_EmployeeIsNull() {
        Employee employee = null;
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> EmployeeService.AddNewEmployee(corporation, employee));
        assertEquals("model.Employee is null", exception.getMessage());
    }

    @Test
    public void testCorp_FindEmployee_Valid() {
        Employee employee  = new Employee("Tadeusz", "Norek", "norek@wp.pl", Position.Intern, 3000f);
        EmployeeService.AddNewEmployee(corporation, employee);
        assertEquals(EmployeeService.FindEmployee(corporation, employee).get(0).getName(), employee.getName());
    }

    @Test
    public void testCorp_SortEmployees_Valid() {
        Employee employee  = new Employee("Tadeusz", "Norek", "norek@wp.pl", Position.Intern, 3000f);
        EmployeeService.AddNewEmployee(corporation, employee);
        Employee employee2  = new Employee("Tadeusz", "Aorekk", "norek2@wp.pl", Position.Intern, 3000f);
        EmployeeService.AddNewEmployee(corporation, employee2);
        EmployeeService.SortEmployee(corporation);
        assertEquals(corporation.getEmployeeList().get(0).getMail(), employee2.getMail());
    }

    @Test
    public void testCorp_GetAverageSalary_Valid() {

        Employee employee  = new Employee("Tadeusz", "Norek", "norek@wp.pl", Position.Intern, 3000f);
        EmployeeService.AddNewEmployee(corporation, employee);
        Employee employee2  = new Employee("Amadeusz", "Bogacz", "prezes@wp.pl", Position.CEO, 25000f);
        EmployeeService.AddNewEmployee(corporation, employee2);
        Float salary = EmployeeService.GetAverageSalary(corporation);
        assertEquals(14000.0, salary.doubleValue(), 0.0001);

    }

    @Test
    public void testCorp_GetMostExpensiveEmployee_Valid() {
        Employee employee  = new Employee("Tadeusz", "Norek", "norek@wp.pl", Position.Intern, 3000f);
        EmployeeService.AddNewEmployee(corporation, employee);
        Employee employee2  = new Employee("Amadeusz", "Bogacz", "prezes@wp.pl", Position.CEO, 25000f);
        EmployeeService.AddNewEmployee(corporation, employee2);
        assertEquals(employee2.getMail(), EmployeeService.FindMostExpensiveEmployee(corporation).getMail());

    }
    @Test
    public void testCorp_GetMostExpensiveEmployee_Invalid_EmptyList() {
        assertEquals(null, EmployeeService.FindMostExpensiveEmployee(corporation));

    }
    @Test
    public void testCorp_getAverageSalary_EmptyList(){
        assertEquals(0f,EmployeeService.GetAverageSalary(corporation));

    }
    @Test
    public void testCorp_getListOfEmployee() {
        Employee emp = new Employee("Tadeusz", "Norek", "mail@wp.pl", Position.Programmer, 8000f);
        EmployeeService.AddNewEmployee(corporation, emp);
        List<String> expected = new ArrayList<>();
        expected.add("Name: Tadeusz, Surname: Norek, Mail: mail@wp.pl, model.Corporation: Corp");
        List<String> result = EmployeeService.GetListOfEmployee(corporation);
        assertEquals(expected, result);


    }
    @Test
    public void testCorp_getCompanyStatistics(){
        Employee emp = new Employee("Tadeusz", "Norek", "mail@wp.pl", Position.Programmer, 8000f);
        EmployeeService.AddNewEmployee(corporation, emp);
        String expected = "{Corp=Amount of Employees: " + " " + String.valueOf(1) + "\n" +
                "Average salary: " + " " + String.valueOf(8000f) + "\n" +
                "Most paid employee: " + " " + "Tadeusz Norek}";
        String result = EmployeeService.getCompanyStatistics(corporation).toString();
        assertEquals(expected, result);

    }
    @Test
    public void testCorp_getAmountOfEachPosition(){
        Employee emp = new Employee("Tadeusz", "Norek", "mail@wp.pl", Position.Programmer, 8000f);
        EmployeeService.AddNewEmployee(corporation, emp);
        Map<Position, List<Employee>> expected = new HashMap<>();
        List<Employee> expectedList = new ArrayList<>();
        expectedList.add(emp);
        expected.put(Position.Programmer, expectedList);
        Map<Position, List<Employee>> result = EmployeeService.GetAmountOfEachPosition(corporation);
        assertEquals(expected.get(Position.Programmer), result.get(Position.Programmer));

    }



}


