package foodwarehouse.core.database;

import foodwarehouse.core.database.rowmappers.AddressResultSetMapper;
import foodwarehouse.core.database.rowmappers.CustomerResultSetMapper;
import foodwarehouse.core.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.core.database.rowmappers.UserResultSetMapper;
import foodwarehouse.core.database.tables.AddressTable;
import foodwarehouse.core.database.tables.CustomerTable;
import foodwarehouse.core.database.tables.EmployeeTable;
import foodwarehouse.core.database.tables.UserTable;
import foodwarehouse.core.user.Permission;
import foodwarehouse.core.user.User;
import foodwarehouse.core.user.UserRepository;
import foodwarehouse.core.user.customer.Customer;
import foodwarehouse.core.user.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import foodwarehouse.core.address.Address;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final String procedureReadUsers = "CALL `GET_USERS`()";
    private final String procedureReadUserById = "CALL `GET_USER_BY_ID`(?)";
    private final String procedureReadUserByUsername = "CALL `GET_USER_BY_USERNAME`(?)";
    private final String procedureReadUserByEmail = "CALL `GET_USER_BY_EMAIL`(?)";

    private final String procedureReadEmploees = "CALL `GET_EMPLOYEES`()";
    private final String procedureReadEmploeeById = "CALL `GET_EMPLOYEE_BY_ID`(?)";

    private final String procedureReadCustomers = "CALL `GET_CUSTOMERS`()";
    private final String procedureReadCustomerById = "CALL `GET_CUSTOMER_BY_ID`(?)";

    private final String procedureReadAddresses = "CALL `GET_ADDRESSES`()";
    private final String procedureReadAddressById = "CALL `GET_ADDRESS_BY_ID`(?)";

    /*================================================================*/
    /*========================= Fields START =========================*/

    private final Connection connection;
    private final JdbcTemplate jdbcTemplate;

    /*========================= Fields END =========================*/
    /*==============================================================*/


    @Autowired
    JdbcUserRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*==============================================================*/
    /*========================= User START =========================*/

    @Override
    public Optional<User> createUser(String username, String password, String email, Permission permission) {
        String query = String.format("INSERT INTO `%s`(`%s`, `%s`, `%s`, `%s`) VALUES (?,?,?,?)",
                UserTable.NAME,
                UserTable.Columns.USERNAME,
                UserTable.Columns.PASSWORD,
                UserTable.Columns.PERMISSION,
                UserTable.Columns.EMAIL);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, permission.value());
                ps.setString(4, email);
                return ps;
            }, keyHolder);

            BigInteger biguid = (BigInteger) keyHolder.getKey();
            int userId = biguid.intValue();
            return Optional.of(new User(userId, username, password, email, permission));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean updateUser(int userId, String username, String password, String email, Permission permission) {
        String query = String.format("UPDATE `%s` SET `%s` = ?, `%s` = ?, `%s` = ?, `%s` = ? WHERE `%s` = ?",
                UserTable.NAME,
                UserTable.Columns.USERNAME,
                UserTable.Columns.PASSWORD,
                UserTable.Columns.PERMISSION,
                UserTable.Columns.EMAIL,
                UserTable.Columns.USER_ID);

        Object[] args = new Object[] {username, password, email, permission.value(), userId};
        int update = jdbcTemplate.update(query, args);

        if(update == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user) {
        String query = String.format("DELETE FROM `%s` WHERE `%s` = ?", UserTable.NAME, UserTable.Columns.USER_ID);
        Object[] args = new Object[] {user.userId()};
        int delete = jdbcTemplate.update(query, args);

        if(delete == 1) {
            user = null;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<User> findAllUsers() throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadUsers);
        ResultSet resultSet = callableStatement.executeQuery();
        List<User> users = new LinkedList<>();
        while(resultSet.next()) {
            users.add(new UserResultSetMapper().resultSetMap(resultSet));
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(int userId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadUserById);
        callableStatement.setInt(1, userId);
        ResultSet resultSet = callableStatement.executeQuery();
        User user = null;
        while(resultSet.next()) {
            user = new UserResultSetMapper().resultSetMap(resultSet);
        }
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByUsername(String username) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadUserByUsername);
        callableStatement.setString(1, username);
        ResultSet resultSet = callableStatement.executeQuery();
        User user = null;
        while(resultSet.next()) {
            user = new UserResultSetMapper().resultSetMap(resultSet);
        }
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadUserByEmail);
        callableStatement.setString(1, email);
        ResultSet resultSet = callableStatement.executeQuery();
        User user = null;
        while(resultSet.next()) {
            user = new UserResultSetMapper().resultSetMap(resultSet);
        }
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }

    /*========================= User END =========================*/
    /*============================================================*/


    /*=================================================================*/
    /*========================= Address START =========================*/

    @Override
    public Optional<Address> createAddress(String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) {
        String query = String.format("INSERT INTO `%s`(`%s`, `%s`, `%s`, `%s`, `%s`, `%s`) VALUES (?,?,?,?,?,?)",
                AddressTable.NAME,
                AddressTable.Columns.COUNTRY,
                AddressTable.Columns.TOWN,
                AddressTable.Columns.POSTAL_CODE,
                AddressTable.Columns.BUILDING_NUMBER,
                AddressTable.Columns.STREET,
                AddressTable.Columns.APARTMENT_NUMBER);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, country);
                ps.setString(2, town);
                ps.setString(3, postalCode);
                ps.setString(4, buildingNumber);
                ps.setString(5, street);
                ps.setString(6, apartmentNumber);
                return ps;
            }, keyHolder);

            BigInteger biguid = (BigInteger) keyHolder.getKey();
            int addressId = biguid.intValue();
            return Optional.of(new Address(addressId, country, town, postalCode, buildingNumber, street, apartmentNumber));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean updateAddress(Address address, String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) {
        return false;
    }

    @Override
    public boolean deleteAddress(Address address) {
        String query = String.format("DELETE FROM `%s` WHERE `%s` = ?", AddressTable.NAME, AddressTable.Columns.ADDRESS_ID);
        Object[] args = new Object[] {address.addressId()};
        int delete = jdbcTemplate.update(query, args);

        if(delete == 1) {
            address = null;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Optional<Address> findAddressById(int addressId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadAddressById);
        callableStatement.setInt(1, addressId);
        ResultSet resultSet = callableStatement.executeQuery();
        Address address = null;
        while(resultSet.next()) {
            address = new AddressResultSetMapper().resultSetMap(resultSet);
        }
        Optional<Address> optionalAddress = Optional.ofNullable(address);
        return optionalAddress;
    }

    /*========================= Address END =========================*/
    /*===============================================================*/


    /*==================================================================*/
    /*========================= Customer START =========================*/

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
            customer = null;
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

    /*========================= Customer END =========================*/
    /*================================================================*/


    /*==================================================================*/
    /*========================= Employee START =========================*/

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


    /*========================= Employee END =========================*/
    /*================================================================*/


    /*=========================================================================*/
    /*========================= CheckConnection START =========================*/

    @Override
    public boolean checkConnection() {
        final String CHECK_SQL_QUERY = "SELECT 1";
        boolean isConnected = true;
        try {
            jdbcTemplate.update(connection -> {
                        final PreparedStatement statement = connection.prepareStatement(CHECK_SQL_QUERY);
                        return statement;
                    });
        } catch (CannotGetJdbcConnectionException e) {
            isConnected = false;
        }
        finally {
            return isConnected;
        }
    }

    /*========================= CheckConnection END =========================*/
    /*=======================================================================*/
}
