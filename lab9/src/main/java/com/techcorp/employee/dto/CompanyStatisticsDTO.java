package com.techcorp.employee.dto;


public class CompanyStatisticsDTO {
    private String companyName;
    private int employeeCount;
    private Float averageSalary;
    private Float highestSalary;
    private String topEarnerName;



    public CompanyStatisticsDTO() {
    }
    public CompanyStatisticsDTO(String companyName, int employeeCount, Float averageSalary, Float highestSalary, String topEarnerName){
        this.companyName = companyName;
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.highestSalary = highestSalary;
        this.topEarnerName = topEarnerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Float getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Float averageSalary) {
        this.averageSalary = averageSalary;
    }

    public Float getHighestSalary() {
        return highestSalary;
    }

    public void setHighestSalary(Float highestSalary) {
        this.highestSalary = highestSalary;
    }

    public String getTopEarnerName() {
        return topEarnerName;
    }

    public void setTopEarnerName(String topEarnerName) {
        this.topEarnerName = topEarnerName;
    }
}
