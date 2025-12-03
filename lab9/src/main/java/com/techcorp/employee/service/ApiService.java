package com.techcorp.employee.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techcorp.employee.exception.ApiException;
import com.techcorp.employee.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ApiService {

    private final EmployeeService employeeService;
    private final HttpClient httpClient;
    private final Gson gson;
    private final ImportSummary importSummary;

    public ApiService(EmployeeService employeeService, HttpClient httpClient, Gson gson, ImportSummary importSummary) {
        this.employeeService = employeeService;
        this.httpClient = httpClient;
        this.gson = gson;
        this.importSummary = importSummary;
    }

    public ImportSummary fetchEmployeesFromAPI(String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ApiException("HTTP error: " + response.statusCode());
        }

        try {

            JsonArray userArray = gson.fromJson(response.body(), JsonArray.class);

            for (JsonElement element : userArray) {
                JsonObject user = element.getAsJsonObject();

                String fullName = user.get("name").getAsString();
                String[] nameParts = fullName.split(" ", 2);
                String firstName = nameParts.length > 0 ? nameParts[0] : "";
                String lastName = nameParts.length > 1 ? nameParts[1] : "";
                String email = user.get("email").getAsString();

                JsonObject company = user.getAsJsonObject("company");
                String companyName = company.get("name").getAsString();

                Employee employee = new Employee(
                        firstName,
                        lastName,
                        email,
                        "",
                        Position.Programmer,
                        Position.Programmer.getSalary(),
                        Status.ACTIVE,
                        ""
                );

                Corporation corp = World.getCorporationList()
                        .stream()
                        .filter(c -> c.getName().equalsIgnoreCase(companyName))
                        .findFirst()
                        .orElseGet(() -> new Corporation(companyName));

                employeeService.addNewEmployee(corp, employee);
                importSummary.addNewImported();
            }

            return importSummary;

        } catch (Exception e) {
            throw new IOException("Error parsing or processing API response", e);
        }
    }
}
