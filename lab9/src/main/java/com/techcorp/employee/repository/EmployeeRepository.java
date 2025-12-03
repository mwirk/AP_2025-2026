package com.techcorp.employee.repository;

import com.techcorp.employee.dto.EmployeeSummaryDTO;
import com.techcorp.employee.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    List<Employee> findByCorporation(String corporation);

    boolean existsByMailAndCorporation(String mail, String corporation);


    @Query("SELECT new com.techcorp.employee.dto.EmployeeSummaryDTO(" +
            " e.name, " +
            " e.surname, " +
            " e.mail, " +
            " e.corporation " +
            ") " +
            "FROM Employee e")
    List<EmployeeSummaryDTO> findAllSummaries();
    Page<Employee> findByCorporation(String corporation, Pageable pageable);


}
