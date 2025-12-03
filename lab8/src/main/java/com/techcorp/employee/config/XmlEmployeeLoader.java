package com.techcorp.employee.loader;



import com.techcorp.employee.model.Employee;
import com.techcorp.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class XmlEmployeeLoader {

    private final List<Employee> xmlEmployees;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public XmlEmployeeLoader(@Qualifier("xmlEmployees") List<Employee> xmlEmployees,
                             EmployeeRepository employeeRepository) {
        this.xmlEmployees = xmlEmployees;
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void loadEmployees() {

        employeeRepository.deleteAll();
        xmlEmployees.forEach(employeeRepository::save);

        System.out.println("Loaded " + xmlEmployees.size() + " employees.");
    }
}
