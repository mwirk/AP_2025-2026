package com.techcorp.employee.controller;

import com.opencsv.exceptions.CsvException;
import com.techcorp.employee.dao.JdbcEmployeeDao;
import com.techcorp.employee.dto.EmployeeViewDTO;
import com.techcorp.employee.model.*;
import com.techcorp.employee.service.FileStorageService;
import com.techcorp.employee.service.ImportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeViewController {

    private final JdbcEmployeeDao employeeDAO;
    private final FileStorageService fileStorageService;
    private final ImportService importService;

    public EmployeeViewController(JdbcEmployeeDao employeeDAO,
                                  FileStorageService fileStorageService,
                                  ImportService importService) {
        this.employeeDAO = employeeDAO;
        this.fileStorageService = fileStorageService;
        this.importService = importService;
    }


    @GetMapping
    public String listEmployees(Model model) {

        List<Employee> employees = employeeDAO.findAll();

        List<EmployeeViewDTO> employeeViewDTOS = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeViewDTO dto = new EmployeeViewDTO(
                    employee.getName(),
                    employee.getSurname(),
                    employee.getMail(),
                    employee.getCorporation(),
                    employee.getPhoto() != null,
                    employee.getPhoto()
            );
            employeeViewDTOS.add(dto);
        }

        model.addAttribute("employees", employeeViewDTOS);
        return "employees/list";
    }


    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeViewDTO("", "", "", "", false, null));
        return "employees/form";
    }


    @PostMapping("/add")
    public String createEmployee(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("mail") String mail,
            @RequestParam("corporation") String corporation,
            @RequestParam("position") Position position,
            @RequestParam("salary") float salary,
            @RequestParam("status") Status status,
            @RequestParam("photo") String photo,
            RedirectAttributes redirectAttributes) {


        if (name.isBlank() || surname.isBlank() || mail.isBlank() || corporation.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Wszystkie pola są wymagane.");
            return "redirect:/employees/add";
        }

        Employee employee = new Employee(
                name,
                surname,
                mail,
                corporation,
                position,
                salary,
                status,
                photo
        );

        employeeDAO.save(employee);

        redirectAttributes.addFlashAttribute(
                "success",
                "Pracownik został dodany do bazy danych."
        );

        return "redirect:/employees";
    }


    @GetMapping("/import")
    public String showImportForm(Model model) {
        return "employees/formFile";
    }

    @PostMapping("/import")
    public String importFromFile(Model model,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam("fileType") String fileType)
            throws IOException, CsvException {

        String pathFile = fileStorageService.storeFile(file);

        // ImportService używa DAO.deleteAll() i DAO.save()
        ImportSummary importSummary = importService.importFromCsv(pathFile);

        model.addAttribute("import", importSummary);
        return "employees/import";
    }
}
