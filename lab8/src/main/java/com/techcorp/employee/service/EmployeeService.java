package com.techcorp.employee.service;

import com.techcorp.employee.model.*;
import com.techcorp.employee.repository.EmployeeRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.techcorp.employee.spec.EmployeeSpecs;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void addNewEmployee(Corporation corporationName, Employee newEmployee) {
        if (newEmployee == null) throw new IllegalArgumentException("Employee is null");

        newEmployee.setCorporation(corporationName.getName());


        if (employeeRepository.existsByMailAndCorporation(newEmployee.getMail(), corporationName.getName())) {
            throw new IllegalArgumentException("Employee with this mail already exists in this corporation");
        }

        employeeRepository.save(newEmployee);
    }

    @Transactional(readOnly = true)
    public List<String> getListOfEmployee() {
        return employeeRepository.findAll().stream()
                .map(employee -> "Name: " + employee.getName() +
                        ", Surname: " + employee.getSurname() +
                        ", Mail: " + employee.getMail() +
                        ", Corporation: " + employee.getCorporation())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Employee> getListOfAllEmployee() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Employee> findEmployee(Employee employeeToFind) {
        if (employeeToFind == null) {
            throw new IllegalArgumentException("Employee is null");
        }

        return employeeRepository.findAll().stream()
                .filter(employee -> employee.equals(employeeToFind))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Employee> sortEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        employees.sort(new EmployeeSorter());
        return employees;
    }

    @Transactional(readOnly = true)
    public Map<Position, List<Employee>> getGroupedListOfEmployee() {
        return employeeRepository.findAll().stream()
                .collect(Collectors.groupingBy(Employee::getPosition));
    }

    @Transactional
    public List<Employee> validateSalaryConsistency() {
        return employeeRepository.findAll().stream()
                .filter(x -> x.getSalary() < x.getPosition().getSalary())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Employee> searchEmployees(String name, String corporation, Float minSalary) {

        Specification<Employee> spec = Specification
                .where(EmployeeSpecs.hasName(name))
                .and(EmployeeSpecs.hasCorporation(corporation))
                .and(EmployeeSpecs.salaryGreaterThan(minSalary));

        return employeeRepository.findAll(spec);
    }

}
