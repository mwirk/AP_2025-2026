package com.techcorp.employee.controller;

import com.techcorp.employee.exception.DuplicateEmailException;
import com.techcorp.employee.exception.EmployeeNotFoundException;
import com.techcorp.employee.model.Corporation;
import com.techcorp.employee.model.Employee;
import com.techcorp.employee.model.Status;
import com.techcorp.employee.model.World;
import com.techcorp.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.library.dto.EmployeeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) String company) {
        List<Employee> employees = employeeService.getListOfAllEmployee();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        for (Employee emp : employees) {
            EmployeeDTO dto = new EmployeeDTO(
                    emp.getName(),
                    emp.getSurname(),
                    emp.getMail(),
                    emp.getCorporation(),
                    emp.getPosition(),
                    emp.getSalary(),
                    emp.getStatus()
            );
            employeeDTOs.add(dto);
        }

        if (company == null || company.isBlank()) {
            return ResponseEntity.ok(employeeDTOs);
        } else {
            return ResponseEntity.ok(
                    employeeDTOs.stream()
                            .filter(x -> x.getCompany().equals(company))
                            .toList()
            );
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.getListOfAllEmployee().stream()
                .filter(x -> x.getMail().equals(email))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found: " + email));

        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO requestEmployeeDTO) {

        boolean exists = employeeService.getListOfAllEmployee().stream()
                .anyMatch(x -> x.getMail().equals(requestEmployeeDTO.getMail()));

        if (exists) {
            throw new DuplicateEmailException("Email already exists: " + requestEmployeeDTO.getMail());
        }

        Employee employee = new Employee(
                requestEmployeeDTO.getName(),
                requestEmployeeDTO.getSurname(),
                requestEmployeeDTO.getMail(),
                requestEmployeeDTO.getCompany(),
                requestEmployeeDTO.getPosition(),
                requestEmployeeDTO.getSalary(),
                requestEmployeeDTO.getStatus()
        );

        Corporation corp = World.getCorporationList().stream()
                .filter(x -> x.getName().equals(employee.getCorporation()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Corporation not found: " + employee.getCorporation()));

        employeeService.addNewEmployee(corp, employee);

        EmployeeDTO response = new EmployeeDTO(
                employee.getName(),
                employee.getSurname(),
                employee.getMail(),
                employee.getCorporation(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{email}")
    public ResponseEntity<EmployeeDTO> updateEmployeesCredentials(
            @RequestBody EmployeeDTO requestEmployeeDTO,
            @PathVariable String email) {

        Employee employee = World.getCorporationList().stream()
                .flatMap(c -> c.getEmployeeList().stream())
                .filter(e -> e.getMail().equals(email))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found: " + email));

        employee.setName(requestEmployeeDTO.getName());
        employee.setSurname(requestEmployeeDTO.getSurname());
        employee.setPosition(requestEmployeeDTO.getPosition());
        employee.setSalary(requestEmployeeDTO.getSalary());
        employee.setStatus(requestEmployeeDTO.getStatus());

        EmployeeDTO response= new EmployeeDTO(
                employee.getName(),
                employee.getSurname(),
                employee.getMail(),
                employee.getCorporation(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getStatus()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<EmployeeDTO> removeEmployee(@PathVariable String email) {
        Employee employeeToBeRemoved = employeeService.getListOfAllEmployee().stream()
                .filter(x -> x.getMail().equals(email))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found: " + email));

        Corporation corp = World.getCorporationList().stream()
                .filter(x -> x.getName().equals(employeeToBeRemoved.getCorporation()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Corporation not found: " + employeeToBeRemoved.getCorporation()));

        corp.getEmployeeList().remove(employeeToBeRemoved);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{email}/status")
    public ResponseEntity<EmployeeDTO> patchStatus(@PathVariable String email, @RequestBody Status status) {
        Employee employee = employeeService.getListOfAllEmployee().stream()
                .filter(x -> x.getMail().equals(email))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found: " + email));

        employee.setStatus(status);

        EmployeeDTO response = new EmployeeDTO(
                employee.getName(),
                employee.getSurname(),
                employee.getMail(),
                employee.getCorporation(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getStatus()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<Employee>> filterByStatus(@PathVariable Status status) {
        List<Employee> list = employeeService.getListOfAllEmployee()
                .stream()
                .filter(x -> x.getStatus().name().equals(status.name()))
                .toList();
        return ResponseEntity.ok(list);
    }
}
