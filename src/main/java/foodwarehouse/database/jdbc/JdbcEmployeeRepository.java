package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeeRepository;
import foodwarehouse.core.data.user.User;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.tables.EmployeeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcEmployeeRepository implements EmployeeRepository {

    private final String procedureInsertEmployee = "CALL `INSERT_EMPLOYEE`(?,?,?,?,?,?)";

    private final String procedureReadEmploees = "CALL `GET_EMPLOYEES`()";
    private final String procedureReadEmploeeById = "CALL `GET_EMPLOYEE_BY_ID`(?)";

    private final Connection connection;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcEmployeeRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureInsertEmployee);
        callableStatement.setInt(1, user.userId());
        callableStatement.setString(2, name);
        callableStatement.setString(3, surname);
        callableStatement.setString(4, position);
        callableStatement.setFloat(5, salary);

        callableStatement.executeQuery();
        int employeeId = callableStatement.getInt(6);

        return Optional.of(new Employee(employeeId, user, name, surname, position, salary));
    }

    @Override
    public Optional<Employee> updateEmployee(int employeeId, User user, String name, String surname, String position, Float salary) {
        String update = String.format("UPDATE `%s` SET `%s` = ?, `%s` = ?, `%s` = ?, `%s` = ? WHERE `%s` = ?",
                EmployeeTable.NAME,
                EmployeeTable.Columns.NAME,
                EmployeeTable.Columns.SURNAME,
                EmployeeTable.Columns.POSITION,
                EmployeeTable.Columns.SALARY,
                EmployeeTable.Columns.EMPLOYEE_ID);

        Object[] args = new Object[] {name, surname, position, salary, employeeId};
        jdbcTemplate.update(update, args);

        return Optional.of(new Employee(employeeId, user, name, surname, position, salary));
    }

    @Override
    public List<Employee> findAllEmployees() throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadEmploees);
        ResultSet resultSet = callableStatement.executeQuery();
        List<Employee> employees = new LinkedList<>();
        while(resultSet.next()) {
            employees.add(new EmployeeResultSetMapper().resultSetMap(resultSet));
        }
        return employees;
    }
}
