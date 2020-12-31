package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.customer.CustomerRepository;
import foodwarehouse.core.data.user.User;
import foodwarehouse.database.rowmappers.CustomerResultSetMapper;
import foodwarehouse.database.tables.CustomerTable;
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
public class JdbcCustomerRepository implements CustomerRepository {

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
    public Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) {
        String query = String.format("INSERT INTO `%s`(`%s`, `%s`, `%s`, `%s`, `%s`, `%s`, `%s`) VALUES (?,?,?,?,?,?,?)",
                CustomerTable.NAME,
                CustomerTable.Columns.USER_ID,
                CustomerTable.Columns.ADDRESS_ID,
                CustomerTable.Columns.NAME,
                CustomerTable.Columns.SURNAME,
                CustomerTable.Columns.FIRMNAME,
                CustomerTable.Columns.PHONE,
                CustomerTable.Columns.TAX);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user.userId());
                ps.setInt(2, address.addressId());
                ps.setString(3, name);
                ps.setString(4, surname);
                ps.setString(5, firmName);
                ps.setString(6, phoneNumber);
                ps.setString(7, taxId);
                return ps;
            }, keyHolder);

            BigInteger biguid = (BigInteger) keyHolder.getKey();
            int customerId = biguid.intValue();
            return Optional.of(new Customer(customerId, user, address, name, surname, firmName, phoneNumber, taxId, 0));
        } catch (Exception e) {
            return Optional.empty();
        }
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
