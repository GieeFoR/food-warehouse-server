package foodwarehouse.core.user;

import foodwarehouse.web.user.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

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

final class CustomerTable {
    static final String NAME = "CUSTOMER";

    static final class Columns {
        static final String CUSTOMER_ID = "CUSTOMER_ID";
        static final String USER_ID = "USER_ID";
        static final String ADDRESS_ID = "ADDRESS_ID";
        static final String NAME = "NAME";
        static final String SURNAME = "SURNAME";
        static final String FIRMNAME = "FIRMNAME";
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
        try {
            String query = String.format("INSERT INTO `%s`(`%s`, `%s`, `%s`, `%s`) VALUES (?,?,?,?)",
                    UserTable.NAME,
                    UserTable.Columns.USERNAME,
                    UserTable.Columns.PASSWORD,
                    UserTable.Columns.PERMISSION,
                    UserTable.Columns.EMAIL);

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
    public Optional<User> createCustomer(int userId, int addressId, String name, String surname, String firmName, String phoneNumber, String taxId) {
        try {
            String query = String.format("INSERT INTO `%s`(`%s`, `%s`, `%s`, `%s`, `%s`, `%s`, `%s`) VALUES (?,?,?,?,?,?,?)",
                    CustomerTable.NAME,
                    CustomerTable.Columns.USER_ID,
                    CustomerTable.Columns.ADDRESS_ID,
                    CustomerTable.Columns.NAME,
                    CustomerTable.Columns.SURNAME,
                    CustomerTable.Columns.FIRMNAME,
                    CustomerTable.Columns.PHONE,
                    CustomerTable.Columns.TAX);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setInt(2, addressId);
                ps.setString(3, name);
                ps.setString(4, surname);
                ps.setString(4, firmName);
                ps.setString(4, phoneNumber);
                ps.setString(4, taxId);
                return ps;
            }, keyHolder);

            BigInteger biguid = (BigInteger) keyHolder.getKey();
            int customerId = biguid.intValue();
            return Optional.of(new Customer(userId, username, password, email, permission));
        } catch (Exception e) {
            return Optional.empty();
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
