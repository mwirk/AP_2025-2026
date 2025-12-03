package com.techcorp.employee.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Column(unique = true, nullable = false)
    private String mail;

    private String corporation;

    @Enumerated(EnumType.STRING)
    private Position position;

    private float salary;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String photo;

    protected Employee() {}

    public Employee(Long id, String name, String surname, String mail,
                    String corporation, Position position, Float salary,
                    Status status, String photo) {

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.corporation = corporation;
        this.position = position;
        this.salary = salary;
        this.status = status;
        this.photo = photo;
    }

    public Employee(String name, String surname, String mail,
                    String corporation, Position position, Float salary,
                    Status status, String photo) {

        this(null, name, surname, mail, corporation, position, salary, status, photo);
    }



    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

    public String getCorporation() { return corporation; }

    public void setCorporation(String corporation) { this.corporation = corporation; }

    public Position getPosition() { return position; }

    public void setPosition(Position position) { this.position = position; }

    public float getSalary() { return salary; }

    public void setSalary(Float salary) { this.salary = salary; }

    public String getPhoto() { return photo; }

    public void setPhoto(String photo) { this.photo = photo; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }


    @Override
    public int hashCode() {
        return mail != null ? mail.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee)) return false;

        Employee other = (Employee) obj;
        return mail != null && mail.equals(other.mail);
    }


    @Override
    public String toString() {
        return "Name: " + name +
                ", Surname: " + surname +
                ", Mail: " + mail +
                ", Corporation: " + corporation;
    }

    public boolean equalsMail(Employee employee) {
        return employee != null && mail.equals(employee.getMail());
    }
}
