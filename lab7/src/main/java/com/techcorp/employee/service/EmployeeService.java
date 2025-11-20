package com.techcorp.employee.service;

import com.techcorp.employee.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    public List<Employee> addNewEmployee(Corporation corporation, Employee newEmployee) {
        if (newEmployee == null) {
            throw new IllegalArgumentException("Employee is null");
        }
        if (corporation == null) {
            throw new IllegalArgumentException("Corporation is null");
        }

        List<Employee> list = corporation.getEmployeeList();
        if (list.stream().anyMatch(employee -> employee.equalsMail(newEmployee))) {
            throw new IllegalArgumentException("Employee with this mail already exists");
        }

        list.add(newEmployee);
        newEmployee.setCorporation(corporation.getName());
        return corporation.getEmployeeList();
    }

    public List<String> getListOfEmployee(Corporation corporation) {
        return corporation.getEmployeeList().stream()
                .map(employee -> "Name: " + employee.getName() +
                        ", Surname: " + employee.getSurname() +
                        ", Mail: " + employee.getMail() +
                        ", Corporation: " + employee.getCorporation())
                .collect(Collectors.toList());
    }
    public List<Employee> getListOfAllEmployee() {

        return World.getCorporationList().stream()
                .map(x -> x.getEmployeeList())
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
    }


    public List<Employee> findEmployee(Corporation corporation, Employee employeeToFind) {
        if (employeeToFind == null) {
            throw new IllegalArgumentException("Employee is null");
        }
        return corporation.getEmployeeList().stream()
                .filter(employee -> employee.equals(employeeToFind))
                .collect(Collectors.toList());
    }

    public List<Employee> sortEmployee(Corporation corporation) {
        corporation.getEmployeeList().sort(new EmployeeSorter());
        return corporation.getEmployeeList();
    }

    public Map<Position, List<Employee>> getGroupedListOfEmployee(Corporation corporation) {
        return corporation.getEmployeeList().stream()
                .collect(Collectors.groupingBy(Employee::getPosition));
    }

    public Map<Position, List<Employee>> getAmountOfEachPosition(Corporation corporation) {
        return corporation.getEmployeeList().stream()
                .collect(Collectors.groupingBy(Employee::getPosition));
    }

    public Float getAverageSalary(Corporation corporation) {
        List<Float> salaries = corporation.getEmployeeList().stream()
                .map(Employee::getSalary)
                .toList();

        Float sum = salaries.stream()
                .reduce(0.0f, Float::sum);

        return sum / corporation.getEmployeeList().size();
    }

    public Employee findMostExpensiveEmployee(Corporation corporation) {
        return corporation.getEmployeeList().stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public List<Employee> validateSalaryConsistency(Corporation corporation) {
        return corporation.getEmployeeList().stream()
                .filter(x -> x.getSalary() < x.getPosition().getSalary())
                .toList();
    }

    public Map<String, CompanyStatistics> getCompanyStatistics(Corporation corporation) {
        Map<String, CompanyStatistics> grouped = new HashMap<>();
        grouped.put(corporation.getName(), new CompanyStatistics(corporation, this));
        return grouped;
    }
    public CompanyStatistics createCompanyStatistics(Corporation corporation) {

        com.techcorp.employee.model.CompanyStatistics companyStatistics =new CompanyStatistics(corporation, this);
        return companyStatistics;
    }


}
