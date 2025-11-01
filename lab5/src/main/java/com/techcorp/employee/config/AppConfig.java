package com.techcorp.employee.config;

import com.google.gson.Gson;
import com.techcorp.employee.model.ImportSummary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.techcorp.employee.service.EmployeeService;

import java.beans.BeanProperty;
import java.net.http.HttpClient;

@Configuration
public class AppConfig {

    @Bean
    public Gson gson(){
        return new Gson();
    }
    @Bean
    public HttpClient httpClient(){
        return HttpClient.newHttpClient();
    }
    @Bean
    public EmployeeService employeeService(){
        return new EmployeeService();
    }
    @Bean
    public ImportSummary importSummary(){
        return new ImportSummary();
    }
}
