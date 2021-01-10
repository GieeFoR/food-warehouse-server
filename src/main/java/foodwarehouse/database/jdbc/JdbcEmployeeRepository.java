package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeeRepository;
import foodwarehouse.core.data.user.User;
import foodwarehouse.database.rowmappers.AddressResultSetMapper;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.tables.EmployeeTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.text.html.Option;
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
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> updateEmployee(int employeeId, User user, String name, String surname, String position, Float salary) {
        try {
            CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.UPDATE);
            callableStatement.setInt(1, employeeId);
            callableStatement.setString(2, name);
            callableStatement.setString(3, surname);
            callableStatement.setString(4, position);
            callableStatement.setFloat(5, salary);

            callableStatement.executeQuery();

            return Optional.of(new Employee(employeeId, user, name, surname, position, salary));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteEmployee(int employeeId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.DELETE);
            callableStatement.setInt(1, employeeId);

            callableStatement.executeQuery();

            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public List<Employee> findAllEmployees() {
        List<Employee> employees = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.READ_ALL);
            ResultSet resultSet = callableStatement.executeQuery();

            while(resultSet.next()) {
                employees.add(new EmployeeResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch (SQLException sqlException) {
            employees = null;
        }
        return employees;
    }

    @Override
    public Optional<Employee> findEmployeeById(int employeeId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, employeeId);

            ResultSet resultSet = callableStatement.executeQuery();
            Employee employee = null;
            if(resultSet.next()) {
                employee = new EmployeeResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(employee);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> findSupplierWithMinDelivery() {
        try {
            CallableStatement callableStatement = connection.prepareCall(EmployeeTable.Procedures.FIND_WITH_MIN_DELIVERY);

            ResultSet resultSet = callableStatement.executeQuery();
            Employee employee = null;
            if(resultSet.next()) {
                employee = new EmployeeResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(employee);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }
}
