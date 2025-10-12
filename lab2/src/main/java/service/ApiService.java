package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import exception.ApiException;
import model.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class ApiService {
    public static ImportSummary fetchEmployeesFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            JsonArray userArray = gson.fromJson(response.body(), JsonArray.class);
            ImportSummary importSummary = new ImportSummary();

            for (JsonElement element : userArray) {
                JsonObject user = element.getAsJsonObject();
                String fullName = user.get("name").getAsString();
                String[] nameParts = fullName.split(" ", 2);
                String firstName = nameParts.length > 0 ? nameParts[0] : "";
                String lastName = nameParts.length > 1 ? nameParts[1] : "";
                String email = user.get("email").getAsString();
                JsonObject company = user.getAsJsonObject("company");
                String companyName = company.get("name").getAsString();

                Employee employee = new Employee(firstName, lastName, email, Position.Programmer, Position.Programmer.getSalary()
                );

                Corporation corp = World.getCorporationList()
                        .stream()
                        .filter(c -> c.getName().equalsIgnoreCase(companyName))
                        .findFirst()
                        .orElseGet(() -> new Corporation(companyName));

                EmployeeService.AddNewEmployee(corp, employee);
                importSummary.addNewImported();
            }
            return importSummary;

        } else {
            throw new ApiException("HTTP error: " + response.statusCode());
        }
    }


}
