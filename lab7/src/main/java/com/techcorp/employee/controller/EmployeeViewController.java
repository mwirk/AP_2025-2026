package com.techcorp.employee.controller;


import com.opencsv.exceptions.CsvException;
import com.techcorp.employee.dto.EmployeeViewDTO;
import com.techcorp.employee.model.*;
import com.techcorp.employee.service.EmployeeService;
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

    private final EmployeeService employeeService;
    private final FileStorageService fileStorageService;
    private final ImportService importService;

    public EmployeeViewController(EmployeeService employeeService,
                             FileStorageService fileStorageService, ImportService importService) {
        this.employeeService = employeeService;
        this.fileStorageService = fileStorageService;
        this.importService = importService;
    }

    @GetMapping
    public String listEmployees(Model model) {

        List<Employee> employees = employeeService.getListOfAllEmployee();


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

        // Walidacja podstawowych danych
        if (name == null || name.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "Imię pracownika jest wymagane");
            return "redirect:/employees/new";
        }
        if (surname == null || surname.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "Nazwisko pracownika jest wymagane");
            return "redirect:/employees/new";
        }
        if (mail == null || mail.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "Mail pracownika jest wymagany");
            return "redirect:/employees/new";
        }
        if (corporation == null || corporation.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "Firma pracownika jest wymagana");
            return "redirect:/employees/new";
        }


        Employee employee = new Employee(name, surname, mail, corporation, position, salary, status,photo);

        Corporation corp = World.getCorporationList().stream()
                .filter(x -> x.getName().equals(employee.getCorporation()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Corporation not found: "));
        employeeService.addNewEmployee(corp, employee);

        redirectAttributes.addFlashAttribute("success",
                "Pracownik został dodany pomyślnie");

        return "redirect:/employees";
    }
    @GetMapping("/import")
    public String showImportForm(Model model) {

        return "employees/formFile";
    }
    @PostMapping("/import")
    public String importFromFile(Model model, @RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) throws IOException, CsvException {
        String pathFile = fileStorageService.storeFile(file);
        ImportSummary importSummary = importService.importFromCsv(pathFile);
        model.addAttribute("import", importSummary);
        return "employees/import";
    }

//    @GetMapping("/{title}")
//    public String showBookDetails(@PathVariable String title, Model model) {
//        // Wyszukanie książki po tytule
//        List<Book> allBooks = bookService.getAllBooks();
//        Book book = allBooks.stream()
//                .filter(b -> b.getTitle().equals(title))
//                .findFirst()
//                .orElse(null);
//
//        if (book == null) {
//            return "redirect:/web/books";
//        }
//
//        // Konwersja na DTO przed przekazaniem do widoku
//        BookViewDTO dto = convertToViewDTO(book);
//        model.addAttribute("book", dto);
//
//        return "books/details";
//    }
//
//    // Metoda pomocnicza do konwersji Book -> BookViewDTO
//    private BookViewDTO convertToViewDTO(Book book) {
//        boolean hasCover = book.getCoverImageFilename() != null;
//        String coverUrl = null;
//
//        if (hasCover) {
//            // Generowanie URL do endpointu REST API pobierającego okładkę
//            coverUrl = "/api/books/" + book.getTitle() + "/cover";
//        }
//
//        return new BookViewDTO(
//                book.getTitle(),
//                book.getAuthor(),
//                book.getYear(),
//                hasCover,
//                coverUrl
//        );
//    }
}