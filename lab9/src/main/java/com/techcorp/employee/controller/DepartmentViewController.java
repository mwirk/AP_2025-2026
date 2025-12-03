package com.techcorp.employee.controller;

import com.techcorp.employee.dto.DepartmentViewDTO;
import com.techcorp.employee.model.Department;
import com.techcorp.employee.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentViewController {

    private final DepartmentService departmentService;

    public DepartmentViewController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public String listDepartments(Model model) {
        List<Department> departments = departmentService.getAllDepartments();

        List<DepartmentViewDTO> departmentViewDTOS = new ArrayList<>();
        for (Department dep : departments) {
            DepartmentViewDTO dto = new DepartmentViewDTO(
                    dep.getName(),
                    dep.getLocation(),
                    dep.getBudget(),
                    dep.getManagerMail()
            );
            departmentViewDTOS.add(dto);
        }

        model.addAttribute("departments", departmentViewDTOS);
        return "departments/list";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("department", new DepartmentViewDTO("", "", 0.0, ""));
        return "departments/form";
    }

    @PostMapping("/add")
    public String createDepartment(
            @RequestParam("name") String name,
            @RequestParam("location") String location,
            @RequestParam("budget") double budget,
            @RequestParam("managerMail") String managerMail,
            RedirectAttributes redirectAttributes) {


        if (name == null || name.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Nazwa działu jest wymagana");
            return "redirect:/departments/add";
        }
        if (location == null || location.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Lokalizacja działu jest wymagana");
            return "redirect:/departments/add";
        }
        if (budget <= 0) {
            redirectAttributes.addFlashAttribute("error", "Budżet musi być większy niż 0");
            return "redirect:/departments/add";
        }
        if (managerMail == null || managerMail.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Mail menedżera jest wymagany");
            return "redirect:/departments/add";
        }

        Department department = new Department(null, name, location, budget, managerMail);
        departmentService.addNewDepartment(department);

        redirectAttributes.addFlashAttribute("success", "Dział został dodany pomyślnie");

        return "redirect:/departments";
    }
}
