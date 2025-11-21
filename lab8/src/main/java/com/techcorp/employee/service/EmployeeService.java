package com.techcorp.employee.service;

import com.techcorp.employee.dao.JdbcEmployeeDao;
import com.techcorp.employee.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final JdbcEmployeeDao employeeDao;

    public EmployeeService(JdbcEmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }



    public void addNewEmployee(Corporation corporationName, Employee newEmployee) {
        if (newEmployee == null) throw new IllegalArgumentException("Employee is null");


        newEmployee.setCorporation(corporationName.getName());

        List<Employee> existingEmployees = employeeDao.findByCorporation(corporationName.getName());
        if (existingEmployees.stream().anyMatch(e -> e.equalsMail(newEmployee))) {
            throw new IllegalArgumentException("Employee with this mail already exists in this corporation");
        }

        employeeDao.save(newEmployee);
    }



    public List<String> getListOfEmployee() {
        return employeeDao.findAll().stream()
                .map(employee -> "Name: " + employee.getName() +
                        ", Surname: " + employee.getSurname() +
                        ", Mail: " + employee.getMail() +
                        ", Corporation: " + employee.getCorporation())
                .collect(Collectors.toList());
    }


    public List<Employee> getListOfAllEmployee() {
        return employeeDao.findAll();
    }


    public List<Employee> findEmployee(Employee employeeToFind) {
        if (employeeToFind == null) {
            throw new IllegalArgumentException("Employee is null");
        }

        return employeeDao.findAll().stream()
                .filter(employee -> employee.equals(employeeToFind))
                .collect(Collectors.toList());
    }


    public List<Employee> sortEmployee() {
        List<Employee> employees = employeeDao.findAll();
        employees.sort(new EmployeeSorter());
        return employees;
    }


    public Map<Position, List<Employee>> getGroupedListOfEmployee() {
        return employeeDao.findAll().stream()
                .collect(Collectors.groupingBy(Employee::getPosition));
    }




    public List<Employee> validateSalaryConsistency() {
        return employeeDao.findAll().stream()
                .filter(x -> x.getSalary() < x.getPosition().getSalary())
                .collect(Collectors.toList());
    }



}
