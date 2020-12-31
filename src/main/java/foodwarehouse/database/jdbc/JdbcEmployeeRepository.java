package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeeRepository;
import foodwarehouse.core.data.user.User;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.tables.EmployeeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcEmployeeRepository implements EmployeeRepository {

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
    public Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary) {
        String query = String.format("INSERT INTO `%s`(`%s`, `%s`, `%s`, `%s`, `%s`) VALUES (?,?,?,?,?)",
                EmployeeTable.NAME,
                EmployeeTable.Columns.USER_ID,
                EmployeeTable.Columns.NAME,
                EmployeeTable.Columns.SURNAME,
                EmployeeTable.Columns.POSITION,
                EmployeeTable.Columns.SALARY);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user.userId());
                ps.setString(2, name);
                ps.setString(3, surname);
                ps.setString(4, position);
                ps.setFloat(5, salary);
                return ps;
            }, keyHolder);

            BigInteger biguid = (BigInteger) keyHolder.getKey();
            int employeeId = biguid.intValue();
            return Optional.of(new Employee(employeeId, user, name, surname, position, salary));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean updateEmployee(Employee employee, User user, String name, String surname, String position, Float salary) {
        return false;
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
