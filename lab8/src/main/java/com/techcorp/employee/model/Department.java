package com.techcorp.employee.model;

public class Department {
    private Long id;
    private String name;
    private String location;
    private double budget;
    private String managerMail;

    public Department(Long id, String name, String location, double budget, String managerMail){
        this.id = id;
        this.name = name;
        this.location = location;
        this.budget = budget;
        this.managerMail = managerMail;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getManagerMail() {
        return managerMail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setManagerMail(String managerMail) {
        this.managerMail = managerMail;
    }
}
