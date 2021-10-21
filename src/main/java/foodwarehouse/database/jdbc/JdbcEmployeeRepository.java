package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeeRepository;
import foodwarehouse.core.data.user.User;
import foodwarehouse.database.rowmappers.AddressResultSetMapper;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.EmployeeTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcEmployeeRepository implements EmployeeRepository {

    private final Connection connection;

    @Autowired
    JdbcEmployeeRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }


    @Override
    public Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("employee"), Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user.userId());
            statement.setString(2, name);
            statement.setString(3, surname);
            statement.setString(4, position);
            statement.setFloat(5, salary);

            statement.executeUpdate();
            int employeeId = statement.getGeneratedKeys().getInt(1);

            return Optional.of(new Employee(employeeId, user, name, surname, position, salary));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> updateEmployee(int employeeId, User user, String name, String surname, String position, Float salary) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("employee"));
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, position);
            statement.setFloat(4, salary);
            statement.setInt(5, employeeId);

            statement.executeUpdate();

            return Optional.of(new Employee(employeeId, user, name, surname, position, salary));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteEmployee(int employeeId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("employee"));
            statement.setInt(1, employeeId);

            statement.executeUpdate();

            return true;
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return false;
        }
    }

    @Override
    public List<Employee> findAllEmployees() {
        List<Employee> employees = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("employee"));

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                employees.add(new EmployeeResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch (SQLException | FileNotFoundException sqlException) {
            employees = null;
        }
        return employees;
    }

    @Override
    public Optional<Employee> findEmployeeById(int employeeId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("employee_byId"));
            statement.setInt(1, employeeId);

            ResultSet resultSet = statement.executeQuery();
            Employee employee = null;
            if(resultSet.next()) {
                employee = new EmployeeResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(employee);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> findEmployeeByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("employee_byUsername"));
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            Employee employee = null;
            if(resultSet.next()) {
                employee = new EmployeeResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(employee);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> findSupplierWithMinDelivery() {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("employee_withMinDeliveries"));

            ResultSet resultSet = statement.executeQuery();
            Employee employee = null;
            if(resultSet.next()) {
                employee = new EmployeeResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(employee);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }
}
