package foodwarehouse.core.user;

import foodwarehouse.core.user.customer.Customer;
import foodwarehouse.core.user.employee.Employee;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import foodwarehouse.core.address.Address;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

final class UserTable {
    static final String NAME = "USER";

    static final class Columns {
        static final String USER_ID = "USER_ID";
        static final String USERNAME = "USERNAME";
        static final String PASSWORD = "PASSWORD";
        static final String PERMISSION = "PERMISSION";
        static final String EMAIL = "E_MAIL";
    }
}

final class AddressTable {
    static final String NAME = "ADDRESS";

    static final class Columns {
        static final String ADDRESS_ID = "ADDRESS_ID";
        static final String COUNTRY = "COUNTRY";
        static final String TOWN = "TOWN";
        static final String POSTAL_CODE = "POSTAL_CODE";
        static final String BUILDING_NUMBER = "BUILDING_NO";
        static final String STREET = "STREET";
        static final String APARTMENT_NUMBER = "APARTMENT_NO";
    }
}

final class CustomerTable {
    static final String NAME = "CUSTOMER";

    static final class Columns {
        static final String CUSTOMER_ID = "CUSTOMER_ID";
        static final String USER_ID = "USER_ID";
        static final String ADDRESS_ID = "ADDRESS_ID";
        static final String NAME = "NAME";
        static final String SURNAME = "SURNAME";
        static final String FIRMNAME = "FIRM_NAME";
        static final String PHONE = "TELEPHONE_NO";
        static final String TAX = "TAX_ID";
        static final String DISCOUNT = "DISCOUNT";
    }
}

final class EmployeeTable {
    static final String NAME = "EMPLOYEE";

    static final class Columns {
        static final String EMPLOYEE_ID = "EMPLOYEE_ID";
        static final String USER_ID = "USER_ID";
        static final String NAME = "NAME";
        static final String SURNAME = "SURNAME";
        static final String POSITION = "POSITION";
        static final String SALARY = "SALARY";
    }
}

@Repository
public class JdbcUserRepository implements UserRepository {

    private final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> new User(
            rs.getInt(UserTable.Columns.USER_ID),
            rs.getString(UserTable.Columns.USERNAME),
            rs.getString(UserTable.Columns.PASSWORD),
            rs.getString(UserTable.Columns.EMAIL),
            Permission.from(rs.getString(UserTable.Columns.PERMISSION)).get());

    private final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, rowNum) ->  {
        Optional <User> user = findUserById(rs.getInt(EmployeeTable.Columns.USER_ID));
        if(user.isEmpty()) {
            throw new RestException("Cannot find an Employee user account.");
        }
        return new Employee(
                rs.getInt(EmployeeTable.Columns.EMPLOYEE_ID),
                user.get(),
                rs.getString(EmployeeTable.Columns.NAME),
                rs.getString(EmployeeTable.Columns.SURNAME),
                rs.getString(EmployeeTable.Columns.POSITION),
                rs.getFloat(EmployeeTable.Columns.POSITION));
    };

    private final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) ->  {
        Optional <User> user = findUserById(rs.getInt(CustomerTable.Columns.USER_ID));
        if(user.isEmpty()) {
            throw new RestException("Cannot find an Employee user account.");
        }

        Optional <Address> address = findAddressById(rs.getInt(CustomerTable.Columns.ADDRESS_ID));
        if(user.isEmpty()) {
            throw new RestException("Cannot find an Employee user account.");
        }
        return new Customer(
                rs.getInt(CustomerTable.Columns.CUSTOMER_ID),
                user.get(),
                address.get(),
                rs.getString(CustomerTable.Columns.NAME),
                rs.getString(CustomerTable.Columns.SURNAME),
                rs.getString(CustomerTable.Columns.FIRMNAME),
                rs.getString(CustomerTable.Columns.PHONE),
                rs.getString(CustomerTable.Columns.TAX),
                rs.getInt(CustomerTable.Columns.DISCOUNT));
    };

    private final RowMapper<Address> ADDRESS_ROW_MAPPER = (rs, rowNum) -> new Address(
            rs.getInt(AddressTable.Columns.ADDRESS_ID),
            rs.getString(AddressTable.Columns.COUNTRY),
            rs.getString(AddressTable.Columns.TOWN),
            rs.getString(AddressTable.Columns.POSTAL_CODE),
            rs.getString(AddressTable.Columns.BUILDING_NUMBER),
            rs.getString(AddressTable.Columns.STREET),
            rs.getString(AddressTable.Columns.APARTMENT_NUMBER));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(UserTable.NAME)
                .usingGeneratedKeyColumns(UserTable.Columns.USER_ID);
    }

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
    public Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary) {
        String query = String.format("INSERT INTO `%s`(`%s`, `%s`, `%s`, `%s`, `%s`) VALUES (?,?,?,?,?)",
                EmployeeTable.NAME,
                EmployeeTable.Columns.USER_ID,
                EmployeeTable.Columns.NAME,
                EmployeeTable.Columns.SURNAME,
                EmployeeTable.Columns.POSITION,
                EmployeeTable.Columns.SALARY);
        try {
            System.out.println(query);
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
            System.out.println(biguid);
            int employeeId = biguid.intValue();
            return Optional.of(new Employee(employeeId, user, name, surname, position, salary));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean updateUser(User user, String username, String password, String email, Permission permission) {
        String query = String.format("UPDATE `%s` SET `%s` = ?, `%s` = ?, `%s` = ?, `%s` = ? WHERE `%s` = ?",
                UserTable.NAME,
                UserTable.Columns.USERNAME,
                UserTable.Columns.PASSWORD,
                UserTable.Columns.PERMISSION,
                UserTable.Columns.EMAIL,
                UserTable.Columns.USER_ID);

        Object[] args = new Object[] {username, password, email, permission.value(), user.userId()};
        int update = jdbcTemplate.update(query, args);

        if(update == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateAddress(Address address, String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) {
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer, User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) {
        return false;
    }

    @Override
    public boolean updateEmployee(Employee employee, User user, String name, String surname, String position, Float salary) {
        return false;
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

    @Override
    public List<User> findAllUsers() {
        String query = String.format("SELECT * FROM `%s`", UserTable.NAME);
        return jdbcTemplate.query(query, USER_ROW_MAPPER);
    }

    @Override
    public Optional<User> findUserById(int userId) {
        String query = String.format("SELECT * FROM `%s` where `%s` = ?", UserTable.NAME, UserTable.Columns.USER_ID);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, USER_ROW_MAPPER, userId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> findAllEmployees() {
        String query = String.format("SELECT * FROM `%s`", EmployeeTable.NAME);
        return jdbcTemplate.query(query, EMPLOYEE_ROW_MAPPER);
    }

    @Override
    public List<Customer> findAllCustomers() {
        String query = String.format("SELECT * FROM `%s`", CustomerTable.NAME);
        return jdbcTemplate.query(query, CUSTOMER_ROW_MAPPER);
    }

    @Override
    public Optional<Address> findAddressById(int addressId) {
        String query = String.format("SELECT * FROM `%s` where `%s` = ?", AddressTable.NAME, AddressTable.Columns.ADDRESS_ID);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, ADDRESS_ROW_MAPPER, addressId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        String query = String.format("SELECT * FROM `%s` WHERE `%s` = ?", UserTable.NAME, UserTable.Columns.USERNAME);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, USER_ROW_MAPPER, username));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String query = String.format("SELECT * FROM `%s` WHERE `%s` = ?", UserTable.NAME, UserTable.Columns.EMAIL);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, USER_ROW_MAPPER, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
