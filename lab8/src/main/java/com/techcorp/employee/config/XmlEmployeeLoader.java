package com.techcorp.employee.loader;


import com.techcorp.employee.dao.JdbcEmployeeDao;
import com.techcorp.employee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class XmlEmployeeLoader {

    private final List<Employee> xmlEmployees;
    private final JdbcEmployeeDao employeeDAO;

    @Autowired
    public XmlEmployeeLoader(@Qualifier("xmlEmployees") List<Employee> xmlEmployees,
                             JdbcEmployeeDao employeeDAO) {
        this.xmlEmployees = xmlEmployees;
        this.employeeDAO = employeeDAO;
    }

    @PostConstruct
    public void loadEmployees() {

        employeeDAO.deleteAll();
        xmlEmployees.forEach(employeeDAO::save);

        System.out.println("Loaded " + xmlEmployees.size() + " employees.");
    }
}
