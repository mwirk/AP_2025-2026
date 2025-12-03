package com.techcorp.employee.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmployeeDocument {

    public enum FileType {
        CONTRACT,
        CERTIFICATE,
        ID_CARD,
        OTHER
    }

    private String id;
    private String employeeEmail;
    private String fileName;
    private String originalFileName;
    private FileType fileType;
    private LocalDateTime uploadDate;
    private String filePath;

    public EmployeeDocument() {

    }

    public EmployeeDocument(String employeeEmail, String fileName, String originalFileName,
                            FileType fileType, LocalDateTime uploadDate, String filePath) {
        this.id = UUID.randomUUID().toString();
        this.employeeEmail = employeeEmail;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileType = fileType;
        this.uploadDate = uploadDate;
        this.filePath = filePath;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "EmployeeDocument{" +
                "id='" + id + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", fileName='" + fileName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", fileType=" + fileType +
                ", uploadDate=" + uploadDate +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
