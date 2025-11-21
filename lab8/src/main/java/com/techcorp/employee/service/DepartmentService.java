package com.techcorp.employee.service;

import com.techcorp.employee.model.Department;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final List<Department> departmentList = new ArrayList<>();

    // Dodaj nowy dział
    public List<Department> addNewDepartment(Department newDepartment) {
        if (newDepartment == null) {
            throw new IllegalArgumentException("Department is null");
        }

        // Sprawdzenie czy istnieje dział o tej samej nazwie
        if (departmentList.stream().anyMatch(dep -> dep.getName().equalsIgnoreCase(newDepartment.getName()))) {
            throw new IllegalArgumentException("Department with this name already exists");
        }

        departmentList.add(newDepartment);
        return departmentList;
    }

    // Pobierz wszystkie działy
    public List<Department> getAllDepartments() {
        return new ArrayList<>(departmentList);
    }

    // Znajdź dział po nazwie
    public List<Department> findDepartmentByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name is required");
        }

        return departmentList.stream()
                .filter(dep -> dep.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    // Sortowanie działów po budżecie (malejąco)
    public List<Department> sortDepartmentsByBudget() {
        departmentList.sort(Comparator.comparingDouble(Department::getBudget).reversed());
        return departmentList;
    }

    // Grupowanie działów po lokalizacji
    public Map<String, List<Department>> getGroupedByLocation() {
        return departmentList.stream()
                .collect(Collectors.groupingBy(Department::getLocation));
    }

    // Średni budżet wszystkich działów
    public double getAverageBudget() {
        if (departmentList.isEmpty()) return 0.0;

        double sum = departmentList.stream()
                .mapToDouble(Department::getBudget)
                .sum();
        return sum / departmentList.size();
    }

    // Znajdź dział z największym budżetem
    public Department findLargestBudgetDepartment() {
        return departmentList.stream()
                .max(Comparator.comparingDouble(Department::getBudget))
                .orElse(null);
    }

    // Walidacja budżetu (np. minimum wymagane)
    public List<Department> validateBudget(double minimumBudget) {
        return departmentList.stream()
                .filter(dep -> dep.getBudget() < minimumBudget)
                .collect(Collectors.toList());
    }

    // Statystyki dla firmy (analogicznie do EmployeeService)
    public Map<String, com.techcorp.employee.service.DepartmentStatistics> getDepartmentStatistics() {
        Map<String, com.techcorp.employee.service.DepartmentStatistics> stats = new HashMap<>();
        for (Department dep : departmentList) {
            stats.put(dep.getName(), new com.techcorp.employee.service.DepartmentStatistics(dep));
        }
        return stats;
    }
}
