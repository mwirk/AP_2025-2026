package com.techcorp.employee.spec;

import com.techcorp.employee.model.Employee;
import com.techcorp.employee.model.Position;
import com.techcorp.employee.model.Status;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecs {

    public static Specification<Employee> hasName(String name) {
        return (root, query, builder) -> {
            if (name == null) return null;
            return builder.equal(root.get("name"), name);
        };
    }

    public static Specification<Employee> hasSurname(String surname) {
        return (root, query, builder) -> {
            if (surname == null) return null;
            return builder.equal(root.get("surname"), surname);
        };
    }

    public static Specification<Employee> hasMail(String mail) {
        return (root, query, builder) -> {
            if (mail == null) return null;
            return builder.equal(root.get("mail"), mail);
        };
    }

    public static Specification<Employee> hasCorporation(String corporation) {
        return (root, query, builder) -> {
            if (corporation == null) return null;
            return builder.equal(root.get("corporation"), corporation);
        };
    }

    public static Specification<Employee> hasPosition(Position position) {
        return (root, query, builder) -> {
            if (position == null) return null;
            return builder.equal(root.get("position"), position);
        };
    }

    public static Specification<Employee> hasStatus(Status status) {
        return (root, query, builder) -> {
            if (status == null) return null;
            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<Employee> salaryGreaterThan(Float salary) {
        return (root, query, builder) -> {
            if (salary == null) return null;
            return builder.greaterThan(root.get("salary"), salary);
        };
    }

    public static Specification<Employee> salaryLessThan(Float salary) {
        return (root, query, builder) -> {
            if (salary == null) return null;
            return builder.lessThan(root.get("salary"), salary);
        };
    }
}
