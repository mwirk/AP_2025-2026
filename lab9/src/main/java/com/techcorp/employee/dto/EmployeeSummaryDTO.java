package com.techcorp.employee.dto;

public class EmployeeSummaryDTO {
    private String name;
    private String surname;
    private String mail;
    private String corporation;

    public EmployeeSummaryDTO() {
    }

    public EmployeeSummaryDTO(String name, String surname, String mail,
                           String corporation) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.corporation = corporation;

    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }



    public String getCorporation() {
        return corporation;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }


}
