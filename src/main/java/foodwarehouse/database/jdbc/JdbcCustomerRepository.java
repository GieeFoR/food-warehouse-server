package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.customer.CustomerRepository;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.user.User;
import foodwarehouse.database.rowmappers.CustomerResultSetMapper;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.CustomerTable;
import foodwarehouse.database.tables.EmployeeTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
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
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("customer"), Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user.userId());
            statement.setInt(2, address.addressId());
            statement.setString(3, name);
            statement.setString(4, surname);
            statement.setString(5, firmName);
            statement.setString(6, phoneNumber);
            statement.setString(7, taxId);

            statement.executeUpdate();
            int customerId = statement.getGeneratedKeys().getInt(1);

            return Optional.of(new Customer(customerId, user, address, name, surname, firmName, phoneNumber, taxId, 0));
        }
        catch (SQLException | FileNotFoundException sqlException) {
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
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("customer"));
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, firmName);
            statement.setString(4, phoneNumber);
            statement.setString(5, taxId);
            statement.setInt(6, discount);
            statement.setInt(7, customerId);
            statement.executeUpdate();

            return Optional.of(new Customer(customerId, user, address, name, surname, firmName, phoneNumber, taxId, discount));
        }
        catch(SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("customer"));
            statement.setInt(1, customerId);

            statement.executeUpdate();
            return true;
        }
        catch(SQLException | FileNotFoundException sqlException) {
            return false;
        }
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> customers = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("customer"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                customers.add(new CustomerResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            customers = null;
        }
        return customers;
    }

    @Override
    public Optional<Customer> findCustomerById(int customerId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("customer_byId"));
            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();
            Customer customer = null;
            if(resultSet.next()) {
                customer = new CustomerResultSetMapper().resultSetMap(resultSet, "");
            }

            return Optional.ofNullable(customer);
        }
        catch(SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("customer_byUsername"));
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            Customer customer = null;
            if(resultSet.next()) {
                customer = new CustomerResultSetMapper().resultSetMap(resultSet, "");
            }

            return Optional.ofNullable(customer);
        }
        catch(SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }
}
