package com.techcorp.employee.dao;

import com.techcorp.employee.model.Employee;
import com.techcorp.employee.model.Position;
import com.techcorp.employee.model.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcEmployeeDao implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcEmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }


    @Override
    public Optional<Employee> findByEmail(String email) {
        String sql = "SELECT * FROM employees WHERE mail = ?";

        List<Employee> results = jdbcTemplate.query(sql, new EmployeeRowMapper(), email);

        return results.stream().findFirst();
    }


    @Override
    public void save(Employee employee) {


        if (employee.getId() != null) {
            String updateSql =
                    "UPDATE employees SET name=?, surname=?, mail=?, corporation=?, " +
                            "position=?, salary=?, status=?, photo=? WHERE id=?";

            jdbcTemplate.update(updateSql,
                    employee.getName(),
                    employee.getSurname(),
                    employee.getMail(),
                    employee.getCorporation(),
                    employee.getPosition().name(),
                    employee.getSalary(),
                    employee.getStatus().name(),
                    employee.getPhoto(),
                    employee.getId()
            );
            return;
        }


        String insertSql =
                "INSERT INTO employees (name, surname, mail, corporation, position, salary, status, photo) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(insertSql,
                employee.getName(),
                employee.getSurname(),
                employee.getMail(),
                employee.getCorporation(),
                employee.getPosition().name(),
                employee.getSalary(),
                employee.getStatus().name(),
                employee.getPhoto()
        );
    }


    @Override
    public void delete(String email) {
        String sql = "DELETE FROM employees WHERE mail = ?";
        jdbcTemplate.update(sql, email);
    }


    @Override
    public void deleteAll() {
        String sql = "DELETE FROM employees";
        jdbcTemplate.update(sql);
    }

    @Override
    public List<Employee> findByCorporation(String name) {
        String sql = "SELECT * FROM employees WHERE corporation = ?";
        return jdbcTemplate.query(sql, new EmployeeRowMapper(), name);
    }



    private static class EmployeeRowMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new Employee(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("mail"),
                    rs.getString("corporation"),
                    Position.valueOf(rs.getString("position")),
                    rs.getFloat("salary"),
                    Status.valueOf(rs.getString("status")),
                    rs.getString("photo")
            );
        }
    }
}
