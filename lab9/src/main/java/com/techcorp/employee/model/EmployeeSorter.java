package com.techcorp.employee.model;

import java.util.Comparator;

public class EmployeeSorter implements Comparator<Employee> {
    @Override
    public int compare(Employee i, Employee j) {
        int surnameComparison = i.getSurname().compareTo(j.getSurname());
        if (surnameComparison != 0) {
            return surnameComparison;
        }
        return i.getName().compareTo(j.getName());
    }
}