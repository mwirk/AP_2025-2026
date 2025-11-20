package com.techcorp.employee.dto;

public class EmployeeViewDTO {
    private String name;
    private String surname;
    private String mail;
    private String corporation;
    private boolean hasPhoto;
    private String photo;
    public EmployeeViewDTO() {
    }

    public EmployeeViewDTO(String name, String surname, String mail,
                       String corporation, boolean hasPhoto, String photo) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.corporation = corporation;
        this.hasPhoto = hasPhoto;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public String getCorporation() {
        return corporation;
    }
    public boolean getHasPhoto(){
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
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

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
