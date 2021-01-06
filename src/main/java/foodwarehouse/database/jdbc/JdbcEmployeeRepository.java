package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeeRepository;
import foodwarehouse.core.data.user.User;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.tables.EmployeeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcEmployeeRepository implements EmployeeRepository {

    private final Connection connection;

    @Autowired
    JdbcEmployeeRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }


    @Override
    public Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.INSERT);
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
    public Optional<Employee> updateEmployee(int employeeId, User user, String name, String surname, String position, Float salary) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.UPDATE);
        callableStatement.setInt(1, employeeId);
        callableStatement.setString(2, name);
        callableStatement.setString(3, surname);
        callableStatement.setString(4, position);
        callableStatement.setFloat(5, salary);

        callableStatement.executeQuery();

        return Optional.of(new Employee(employeeId, user, name, surname, position, salary));
    }

    @Override
    public boolean deleteEmployee(int employeeId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.DELETE);
        callableStatement.setInt(1, employeeId);

        callableStatement.executeQuery();
        return true;
    }

    @Override
    public List<Employee> findAllEmployees() throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.READ_ALL);
        ResultSet resultSet = callableStatement.executeQuery();
        List<Employee> employees = new LinkedList<>();
        while(resultSet.next()) {
            employees.add(new EmployeeResultSetMapper().resultSetMap(resultSet, ""));
        }
        return employees;
    }

    @Override
    public Optional<Employee> findEmployee(int employeeId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.READ_BY_ID);
        callableStatement.setInt(1, employeeId);

        ResultSet rs = callableStatement.executeQuery();
        return Optional.ofNullable(new EmployeeResultSetMapper().resultSetMap(rs, ""));
    }
}
