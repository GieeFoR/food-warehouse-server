package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.customer.CustomerRepository;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.user.User;
import foodwarehouse.database.rowmappers.CustomerResultSetMapper;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.tables.CustomerTable;
import foodwarehouse.database.tables.EmployeeTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final Connection connection;

    @Autowired
    JdbcCustomerRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(CustomerTable.Procedures.INSERT);
            callableStatement.setInt(1, user.userId());
            callableStatement.setInt(2, address.addressId());
            callableStatement.setString(3, name);
            callableStatement.setString(4, surname);
            callableStatement.setString(5, firmName);
            callableStatement.setString(6, phoneNumber);
            callableStatement.setString(7, taxId);

            callableStatement.executeQuery();
            int customerId = callableStatement.getInt(8);

            return Optional.of(new Customer(customerId, user, address, name, surname, firmName, phoneNumber, taxId, 0));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> updateCustomer(
            int customerId,
            User user,
            Address address,
            String name,
            String surname,
            String firmName,
            String phoneNumber,
            String taxId,
            int discount) {

        try {
            CallableStatement callableStatement = connection.prepareCall(CustomerTable.Procedures.UPDATE);
            callableStatement.setInt(1, customerId);
            callableStatement.setString(2, name);
            callableStatement.setString(3, surname);
            callableStatement.setString(4, firmName);
            callableStatement.setString(5, phoneNumber);
            callableStatement.setString(6, taxId);
            callableStatement.setInt(7, discount);

            callableStatement.executeQuery();

            return Optional.of(new Customer(customerId, user, address, name, surname, firmName, phoneNumber, taxId, discount));
        }
        catch(SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(CustomerTable.Procedures.DELETE);
            callableStatement.setInt(1, customerId);

            callableStatement.executeQuery();
            return true;
        }
        catch(SQLException sqlException) {
            return false;
        }
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> customers = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(CustomerTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                customers.add(new CustomerResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            customers = null;
        }
        return customers;
    }

    @Override
    public Optional<Customer> findCustomerById(int customerId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(CustomerTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, customerId);

            ResultSet resultSet = callableStatement.executeQuery();
            Customer customer = null;
            if(resultSet.next()) {
                customer = new CustomerResultSetMapper().resultSetMap(resultSet, "");
            }

            return Optional.ofNullable(customer);
        }
        catch(SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        try {
            CallableStatement callableStatement = connection.prepareCall(CustomerTable.Procedures.READ_BY_USERNAME);
            callableStatement.setString(1, username);

            ResultSet resultSet = callableStatement.executeQuery();
            Customer customer = null;
            if(resultSet.next()) {
                customer = new CustomerResultSetMapper().resultSetMap(resultSet, "");
            }

            return Optional.ofNullable(customer);
        }
        catch(SQLException sqlException) {
            return Optional.empty();
        }
    }
}
