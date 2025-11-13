package com.techcorp.employee.model;
import java.util.ArrayList;
import java.util.List;

public class Corporation {
    private String name;
    private List<Employee> employeeList;

    public Corporation(String name){
        this.name = name;
        this.employeeList = new ArrayList<>();
        World.addNewToCorporationList(this);
    }
    public Corporation(String name, List<Employee> employeeList){
        this.name = name;
        this.employeeList = employeeList;
        World.addNewToCorporationList(this);

    }

    public String getName(){
        return name;
    }

    public List<Employee> getEmployeeList(){
        return this.employeeList;
    }
    public void setemployeeList(ArrayList<Employee> newList){
        this.employeeList = newList;
    }
}