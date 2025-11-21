package com.techcorp.employee.controller;


import com.opencsv.exceptions.CsvException;
import com.techcorp.employee.model.*;
import com.techcorp.employee.service.EmployeeService;
import com.techcorp.employee.service.FileStorageService;
import com.techcorp.employee.service.ImportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequestMapping("/api/files")
@RestController
public class FileUploadController {

    private final EmployeeService employeeService;
    private final ImportService importService;
    private final FileStorageService fileStorageService;


    private final Map<String, List<EmployeeDocument>> employeeDocuments = new ConcurrentHashMap<>();

    public FileUploadController(EmployeeService employeeService, ImportService importService, FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
        this.employeeService = employeeService;
        this.importService = importService;
    }


    @PostMapping(value = "/import/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportSummary> createEmployeeWithCover(@RequestParam("file") MultipartFile csvFile) throws IOException, CsvException {
        String pathCSV = fileStorageService.storeFile(csvFile);
        ImportSummary importSummary = importService.importFromCsv(pathCSV);
        return ResponseEntity.status(HttpStatus.CREATED).body(importSummary);
    }



    @GetMapping("/export/csv")
    public ResponseEntity<Resource> exportCsvFile(@RequestParam(value = "company", required = false) String company) {
        try {
            List<Employee> employees;
            if (company != null && !company.isEmpty()) {
                Corporation corp = World.getCorporationList().stream()
                        .filter(x -> x.getName().equals(company))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Corporation not found: "));
                employees = corp.getEmployeeList();
            } else {
                employees = employeeService.getListOfAllEmployee();
            }

            StringBuilder csvBuilder = new StringBuilder();
            csvBuilder.append("Name,Surname,Mail,Corporation,Position,Salary,Status\n");
            for (Employee e : employees) {
                csvBuilder
                        .append(e.getName()).append(",")
                        .append(e.getSurname()).append(",")
                        .append(e.getMail()).append(",")
                        .append(e.getCorporation()).append(",")
                        .append(e.getPosition() != null ? e.getPosition().name() : "").append(",")
                        .append(e.getSalary()).append(",")
                        .append(e.getStatus() != null ? e.getStatus().name() : "")
                        .append("\n");
            }

            byte[] csvBytes = csvBuilder.toString().getBytes("UTF-8");
            ByteArrayResource resource = new ByteArrayResource(csvBytes);

            HttpHeaders headers = new HttpHeaders();
            String fileName = (company != null && !company.isEmpty())
                    ? "employees_" + company + ".csv"
                    : "employees.csv";

            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(csvBytes.length)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/documents/{email}")
    public ResponseEntity<EmployeeDocument> uploadDocument(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") EmployeeDocument.FileType type) throws IOException {


        Employee employee = employeeService.getListOfAllEmployee().stream()
                .filter(e -> e.getMail().equals(email))
                .findFirst()
                .orElse(null);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        Path employeeDir = Paths.get("uploads/documents").resolve(email).toAbsolutePath();
        Files.createDirectories(employeeDir);


        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf('.') + 1) : "";
        String uniqueFilename = UUID.randomUUID() + "." + extension;
        Path targetPath = employeeDir.resolve(uniqueFilename);

        Files.copy(file.getInputStream(), targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);


        EmployeeDocument doc = new EmployeeDocument();
        doc.setId(UUID.randomUUID().toString());
        doc.setEmployeeEmail(email);
        doc.setFileName(uniqueFilename);
        doc.setOriginalFileName(originalFilename);
        doc.setFileType(type);
        doc.setUploadDate(java.time.LocalDateTime.now());

        doc.setFilePath(targetPath.toString());


        employeeDocuments.computeIfAbsent(email, k -> new ArrayList<>()).add(doc);

        return ResponseEntity.status(HttpStatus.CREATED).body(doc);
    }


    @GetMapping("/documents/{email}")
    public ResponseEntity<List<EmployeeDocument>> getEmployeeDocuments(@PathVariable String email) {
        if (!employeeDocuments.containsKey(email)) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(employeeDocuments.get(email));
    }

    @GetMapping("/documents/{email}/{documentId}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable String email, @PathVariable String documentId) throws IOException {
        List<EmployeeDocument> docs = employeeDocuments.get(email);
        if (docs == null) {
            return ResponseEntity.notFound().build();
        }

        EmployeeDocument doc = docs.stream()
                .filter(d -> d.getId().equals(documentId))
                .findFirst()
                .orElse(null);

        if (doc == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Path.of(doc.getFilePath());
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new ByteArrayResource(Files.readAllBytes(path));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getOriginalFileName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Files.size(path))
                .body(resource);
    }

    @DeleteMapping("/documents/{email}/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String email, @PathVariable String documentId) throws IOException {
        List<EmployeeDocument> docs = employeeDocuments.get(email);
        if (docs == null) {
            return ResponseEntity.notFound().build();
        }

        EmployeeDocument doc = docs.stream()
                .filter(d -> d.getId().equals(documentId))
                .findFirst()
                .orElse(null);

        if (doc == null) {
            return ResponseEntity.notFound().build();
        }


        Path path = Path.of(doc.getFilePath());
        Files.deleteIfExists(path);


        docs.remove(doc);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/photos/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile photo, @PathVariable String email) throws IOException {
        String pathPhoto = fileStorageService.storeFile(photo);

        Employee employee = employeeService.getListOfAllEmployee().stream()
                .filter(x -> x.getMail().equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        employee.setPhoto(pathPhoto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pathPhoto);
    }
    @GetMapping("/photos/{email}")
    public ResponseEntity<Resource> getEmployeePhoto(@PathVariable String email) throws IOException {


        Employee employee = employeeService.getListOfAllEmployee().stream()
                .filter(e -> e.getMail().equals(email))
                .findFirst()
                .orElse(null);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        String photoPath = employee.getPhoto();
        if (photoPath == null || photoPath.isBlank()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = null;


        Path uploaded = Paths.get("uploads").resolve(photoPath).toAbsolutePath();
        if (Files.exists(uploaded)) {
            resource = new ByteArrayResource(Files.readAllBytes(uploaded));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + uploaded.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(uploaded))
                    .contentLength(Files.size(uploaded))
                    .body(resource);
        }


        org.springframework.core.io.Resource classpathRes =
                new org.springframework.core.io.ClassPathResource("data/" + photoPath);
        if (classpathRes.exists()) {
            resource = classpathRes;
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + classpathRes.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(classpathRes.getFile().toPath()))
                    .contentLength(classpathRes.getFile().length())
                    .body(resource);
        }


        return ResponseEntity.notFound().build();
    }



}

