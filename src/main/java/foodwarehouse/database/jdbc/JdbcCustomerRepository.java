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

    private final String procedureInsertCustomer = "CALL `INSERT_CUSTOMER`(?,?,?,?,?,?,?,?)";

    private final String procedureReadCustomers = "CALL `GET_CUSTOMERS`()";
    private final String procedureReadCustomerById = "CALL `GET_CUSTOMER_BY_ID`(?)";

    private final Connection connection;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcCustomerRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureInsertCustomer);
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
    public boolean updateCustomer(Customer customer, User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) {
        return false;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        String query = String.format("DELETE FROM `%s` WHERE `%s` = ?", CustomerTable.NAME, CustomerTable.Columns.CUSTOMER_ID);
        Object[] args = new Object[] {customer.customerId()};
        int delete = jdbcTemplate.update(query, args);

        if(delete == 1) {
            //customer = null;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<Customer> findAllCustomers() throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadCustomers);
        ResultSet resultSet = callableStatement.executeQuery();
        List<Customer> customers = new LinkedList<>();
        while(resultSet.next()) {
            customers.add(new CustomerResultSetMapper().resultSetMap(resultSet));
        }
        return customers;
    }
}
