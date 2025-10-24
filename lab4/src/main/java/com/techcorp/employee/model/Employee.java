package com.techcorp.employee.model;

public class Employee {
    private String name;
    private String surname;
    private String mail;
    private String corporation;
    private Position position;
    private float salary;

    public Employee(String name, String surname, String mail,  Position position, Float salary){
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.position = position;
        this.salary = salary;
    }
    public String getName(){
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getMail(){
        return this.mail;
    }
    public String getCorporation(){
        return this.corporation;
    }
    public Position getPosition(){
        return this.position;
    }
    public float getSalary() {return salary;}
    public void setCorporation(String corporation){
        this.corporation = corporation;
    }
    public void setName(String newName){
        this.name = name;
    }



    @Override
    public int hashCode(){
        return (this.getSurname() + this.getName() + this.getMail()).hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Employee))
            return false;
        Employee otheremployee = (Employee)obj;
        return otheremployee.getMail() == this.getMail() || otheremployee.getName() == this.getName()
                || otheremployee.getSurname() == this.getSurname();


    }

    @Override
    public String toString() {
        return "Name: " + this.getName() +
                ", Surname: " + this.getSurname() +
                ", Mail: " + this.getMail() +
                ", com.techcorp.employee.model.Corporation: " + this.getCorporation();
    }

    public boolean equalsMail(Employee employee){
        return employee.getMail() == this.getMail();
    }
}
