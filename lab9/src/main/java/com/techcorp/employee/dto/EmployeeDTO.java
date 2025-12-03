package com.example.library.dto;

import com.techcorp.employee.model.Position;
import com.techcorp.employee.model.Status;

public class EmployeeDTO {
    private String name;
    private String surname;
    private String mail;
    private String company;
    private Position position;
    private Float salary;
    private Status status;
    private String photo;


    public EmployeeDTO() {
    }

    public EmployeeDTO(String name, String surname, String mail, String company, Position position, Float salary, Status status, String photo) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.company = company;
        this.position = position;
        this.salary = salary;
        this.status = status;
        this.photo = photo;
    }
    public EmployeeDTO(String name, String surname, String mail, String company, Position position, Float salary, Status status) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.company = company;
        this.position = position;
        this.salary = salary;
        this.status = status;
        this.photo = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }
    public Status getStatus(){
        return status;
    }
    public void setStatus(Status status){
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}