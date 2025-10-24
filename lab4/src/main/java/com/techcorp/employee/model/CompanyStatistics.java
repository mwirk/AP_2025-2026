package com.techcorp.employee.model;

import com.techcorp.employee.service.EmployeeService;

public class CompanyStatistics {
    private int amountEmployees;
    private float averageSalary;
    private String mostWealthEmployee;
    private final EmployeeService employeeService;

    public CompanyStatistics(Corporation corporation, EmployeeService employeeService){
        this.employeeService = employeeService;
        this.amountEmployees = corporation.getEmployeeList().size();
        this.averageSalary = employeeService.getAverageSalary(corporation);
        Employee mostExpensive = employeeService.findMostExpensiveEmployee(corporation);
        this.mostWealthEmployee = mostExpensive.getName() + " " + mostExpensive.getSurname();
    }
    public int getAmountEmployees(){
        return this.amountEmployees;
    }
    public float getAverageSalary(){
        return this.averageSalary;
    }
    public String getMostWealthEmployee(){
        return this.mostWealthEmployee;
    }

    @Override
    public String toString() {
        return "Amount of Employees: " + " " + String.valueOf(this.amountEmployees) + "\n" +
                "Average salary: " + " " +String.valueOf(this.averageSalary) + "\n" +
                "Most paid employee: " + " " +this.mostWealthEmployee;
    }
}
