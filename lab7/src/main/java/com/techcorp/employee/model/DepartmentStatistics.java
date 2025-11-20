package com.techcorp.employee.service;

import com.techcorp.employee.model.Department;

public class DepartmentStatistics {

    private final String name;
    private final double budget;
    private final String location;

    public DepartmentStatistics(Department department) {
        this.name = department.getName();
        this.budget = department.getBudget();
        this.location = department.getLocation();
    }

    public String getName() { return name; }
    public double getBudget() { return budget; }
    public String getLocation() { return location; }
}
