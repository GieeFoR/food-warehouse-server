package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.customer.CustomerRepository;
import foodwarehouse.core.data.user.User;
import foodwarehouse.database.rowmappers.CustomerResultSetMapper;
import foodwarehouse.database.tables.CustomerTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final Connection connection;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcCustomerRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) throws SQLException {
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

    @Override
    public Optional<Customer> updateCustomer(int customerId, User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(CustomerTable.Procedures.UPDATE);
        callableStatement.setInt(1, customerId);
        callableStatement.setString(2, name);
        callableStatement.setString(3, surname);
        callableStatement.setString(4, firmName);
        callableStatement.setString(5, phoneNumber);
        callableStatement.setString(6, taxId);

        callableStatement.executeQuery();
        Integer discount = callableStatement.getInt(7);

        return Optional.of(new Customer(customerId, user, address, name, surname, firmName, phoneNumber, taxId, discount));
    }

    @Override
    public boolean deleteCustomer(int customerId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(CustomerTable.Procedures.DELETE);
        callableStatement.setInt(1, customerId);

        callableStatement.executeQuery();
        return true;
    }

    @Override
    public List<Customer> findAllCustomers() throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(CustomerTable.Procedures.READ_ALL);
        ResultSet resultSet = callableStatement.executeQuery();
        List<Customer> customers = new LinkedList<>();
        while(resultSet.next()) {
            customers.add(new CustomerResultSetMapper().resultSetMap(resultSet, ""));
        }
        return customers;
    }
}
