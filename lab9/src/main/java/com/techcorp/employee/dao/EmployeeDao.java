package com.techcorp.employee.dao;

import com.techcorp.employee.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> findAll();

    Optional<Employee> findByEmail(String email);

    void save(Employee employee); // INSERT lub UPDATE

    void delete(String email);

    void deleteAll();

    List<Employee> findByCorporation(String name);
}
