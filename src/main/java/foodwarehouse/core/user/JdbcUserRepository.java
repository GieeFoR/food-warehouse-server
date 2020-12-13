package foodwarehouse.core.user;

import foodwarehouse.core.user.customer.Customer;
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

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> new User(
            rs.getInt(UserTable.Columns.USER_ID),
            rs.getString(UserTable.Columns.USERNAME),
            rs.getString(UserTable.Columns.PASSWORD),
            rs.getString(UserTable.Columns.EMAIL),
            Permission.from(rs.getString(UserTable.Columns.PERMISSION)).get());

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
    public boolean deleteUser(User user) {
        String sql = String.format("DELETE FROM `%s` WHERE `%s` = ?", UserTable.NAME, UserTable.Columns.USER_ID);
        Object[] args = new Object[] {user.userId()};
        int delete = jdbcTemplate.update(sql, args);

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
        String sql = String.format("DELETE FROM `%s` WHERE `%s` = ?", AddressTable.NAME, AddressTable.Columns.ADDRESS_ID);
        Object[] args = new Object[] {address.addressId()};
        int delete = jdbcTemplate.update(sql, args);

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
        String sql = String.format("DELETE FROM `%s` WHERE `%s` = ?", CustomerTable.NAME, CustomerTable.Columns.CUSTOMER_ID);
        Object[] args = new Object[] {customer.customerId()};
        int delete = jdbcTemplate.update(sql, args);

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
    public List<User> findAll() {
        String query = String.format("SELECT * FROM `%s`", UserTable.NAME);
        return jdbcTemplate.query(query, USER_ROW_MAPPER);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String query = String.format("SELECT * FROM `%s` WHERE `%s` = ?", UserTable.NAME, UserTable.Columns.USERNAME);
        try {

            return Optional.ofNullable(jdbcTemplate.queryForObject(query, USER_ROW_MAPPER, username));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = String.format("SELECT * FROM `%s` WHERE `%s` = ?", UserTable.NAME, UserTable.Columns.EMAIL);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, USER_ROW_MAPPER, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
