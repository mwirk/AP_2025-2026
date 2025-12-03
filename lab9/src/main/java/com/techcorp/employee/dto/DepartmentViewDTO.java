package com.techcorp.employee.dto;

public class DepartmentViewDTO {
    private String name;
    private String location;
    private double budget;
    private String managerMail;

    public DepartmentViewDTO(String name, String location, double budget, String managerMail) {
        this.name = name;
        this.location = location;
        this.budget = budget;
        this.managerMail = managerMail;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public double getBudget() { return budget; }
    public String getManagerMail() { return managerMail; }

    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setBudget(double budget) { this.budget = budget; }
    public void setManagerMail(String managerMail) { this.managerMail = managerMail; }
}
