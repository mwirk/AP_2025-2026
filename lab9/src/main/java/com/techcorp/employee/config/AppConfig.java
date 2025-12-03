package com.techcorp.employee.config;

import com.google.gson.Gson;

import com.techcorp.employee.model.ImportSummary;
import com.techcorp.employee.repository.EmployeeRepository;
import com.techcorp.employee.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.net.http.HttpClient;

@Configuration
public class AppConfig {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }


    @Bean
    public EmployeeService employeeService(EmployeeRepository employeeRepository) {
        return new EmployeeService(employeeRepository);
    }

    @Bean
    public ImportSummary importSummary() {
        return new ImportSummary();
    }
}
