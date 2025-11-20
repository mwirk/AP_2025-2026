package com.techcorp.employee.controller;

import com.techcorp.employee.dto.CompanyStatisticsDTO;
import com.techcorp.employee.exception.InvalidDataException;
import com.techcorp.employee.model.*;
import com.techcorp.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    private final EmployeeService employeeService;

    public StatisticController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/salary/average")
    public ResponseEntity<Map<String, Float>> getAverageSalaries(@RequestParam(required = false) String company) throws InvalidDataException {
        Map<String, Float> map = new HashMap<>();

        if (company == null || company.isBlank()) {
            World.getCorporationList().forEach(corporation -> {
                float avgSalary = employeeService.getAverageSalary(corporation);
                map.put(corporation.getName(), avgSalary);
            });
        } else {
            Corporation corp = World.getCorporationList()
                    .stream()
                    .filter(x -> x.getName().equals(company))
                    .findFirst()
                    .orElseThrow(() -> new InvalidDataException("Company not found: " + company));

            float avgSalary = employeeService.getAverageSalary(corp);
            map.put(corp.getName(), avgSalary);
        }

        return ResponseEntity.ok(map);
    }

    @GetMapping("/company/{companyName}")
    public ResponseEntity<CompanyStatisticsDTO> getFullStatistics(@PathVariable String companyName) throws InvalidDataException {
        Corporation corp = World.getCorporationList().stream()
                .filter(x -> x.getName().equals(companyName))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("Company not found: " + companyName));

        CompanyStatistics companyStatistics = employeeService.createCompanyStatistics(corp);

        CompanyStatisticsDTO companyStatisticsDTO = new CompanyStatisticsDTO(
                corp.getName(),
                companyStatistics.getAmountEmployees(),
                companyStatistics.getAverageSalary(),
                employeeService.findMostExpensiveEmployee(corp).getSalary(),
                companyStatistics.getMostWealthEmployee()
        );

        return ResponseEntity.ok(companyStatisticsDTO);
    }

    @GetMapping("/positions")
    public ResponseEntity<Map<String, Integer>> getGroupedPositions() {
        Map<String, Integer> positionCounts = World.getCorporationList().stream()
                .flatMap(corp -> corp.getEmployeeList().stream())
                .collect(Collectors.groupingBy(
                        e -> e.getPosition().name(),
                        Collectors.summingInt(e -> 1)
                ));

        return ResponseEntity.ok(positionCounts);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Integer>> getGroupedStatus() {
        Map<String, Integer> statusCounts = World.getCorporationList().stream()
                .flatMap(corp -> corp.getEmployeeList().stream())
                .collect(Collectors.groupingBy(
                        e -> e.getStatus().name(),
                        Collectors.summingInt(e -> 1)
                ));

        return ResponseEntity.ok(statusCounts);
    }

}
