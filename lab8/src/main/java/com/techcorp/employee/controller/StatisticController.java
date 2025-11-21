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
