package model;

import service.EmployeeService;

public class CompanyStatistics {
    private int amountEmployees;
    private float averageSalary;
    private String mostWealthEmployee;

    public CompanyStatistics(Corporation corporation){
        this.amountEmployees = corporation.getEmployeeList().size();
        this.averageSalary = EmployeeService.GetAverageSalary(corporation);
        this.mostWealthEmployee = EmployeeService.FindMostExpensiveEmployee(corporation).getName() + " " + EmployeeService.FindMostExpensiveEmployee(corporation).getSurname();
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
