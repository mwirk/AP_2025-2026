package service;

import model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeService{


    public static List<Employee> AddNewEmployee(Corporation corporation, Employee newEmployee){
        if (newEmployee != null) {
            List<Employee> list = corporation.getEmployeeList();
            if (list.stream().anyMatch(Employee -> Employee.equalsMail(newEmployee))) {
                throw new IllegalArgumentException("model.Employee with this mail already exists");
            } else {
                list.add(newEmployee);
                newEmployee.setCorporation(corporation.getName());
                return corporation.getEmployeeList();
            }
        }
        else {
            throw new IllegalArgumentException("model.Employee is null");
        }

    }

    public static List<String> GetListOfEmployee(Corporation corporation){

        return corporation.getEmployeeList().stream()
                    .map(Employee -> "Name: " + Employee.getName() +
                            ", Surname: " + Employee.getSurname() +
                            ", Mail: " + Employee.getMail() +
                            ", model.Corporation: " + Employee.getCorporation()).collect(Collectors.toList());


    }

    public static List<Employee> FindEmployee(Corporation corporation, Employee EmployeeToFind){
        if (EmployeeToFind != null && corporation != null){
            return corporation.getEmployeeList().stream().filter(Employee -> Employee.equals(EmployeeToFind)).collect(Collectors.toList());

        }
        else {
            if (EmployeeToFind == null){
                throw new IllegalArgumentException("model.Employee is null");
            }
            throw new IllegalArgumentException("model.Corporation is null");
        }



    }
    public static List<Employee> SortEmployee(Corporation corporation){
        Collections.sort(corporation.getEmployeeList(), new EmployeeSorter());
        return corporation.getEmployeeList();

    }

    public static Map<Position, List<Employee>> GetGrouppedListOfEmployee(Corporation corporation){
        Map<Position, List<Employee>> grouped = corporation.getEmployeeList().stream()
                .collect(Collectors.groupingBy(Employee::getPosition));
        return grouped;
    }

    public static Map<Position, List<Employee>> GetAmountOfEachPosition(Corporation corporation){
        Map<Position, List<Employee>> grouped = corporation.getEmployeeList().stream()
                .collect(Collectors.groupingBy(Employee::getPosition));

        return grouped;
    }

    public static Float GetAverageSalary(Corporation corporation){
        if (corporation != null) {
            if (corporation.getEmployeeList().size() > 0) {
                List<Float> salaries = corporation.getEmployeeList().stream()
                        .map(Employee::getSalary)
                        .toList();
                Float sum = salaries.stream()
                        .reduce(0.0f, (a, b) -> a + b);
                return sum / corporation.getEmployeeList().size();
            } else {
                return 0f;
            }
        }
        else{
            throw new IllegalArgumentException("model.Corporation is null");
        }
    }

    public static Employee FindMostExpensiveEmployee(Corporation corporation){

        Optional<Employee> maxEmployee = corporation.getEmployeeList().stream()
                    .max(Comparator.comparing(Employee::getSalary));

            return maxEmployee.orElse(null);

    }

    public static List<Employee> validateSalaryConsistency(Corporation corporation){
        return corporation.getEmployeeList().stream().filter(x -> x.getSalary() < x.getPosition().getSalary()).toList();
    }

    public static Map<String, CompanyStatistics> getCompanyStatistics(Corporation corporation){
        Map<String, CompanyStatistics> grouped = new HashMap<String, CompanyStatistics>();
        grouped.put(corporation.getName(), new CompanyStatistics(corporation));
        return grouped;
    }

}
















