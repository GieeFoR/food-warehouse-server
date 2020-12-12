package foodwarehouse.core.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
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
            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(Map.of(
                    UserTable.Columns.USERNAME, username,
                    UserTable.Columns.PASSWORD, password,
                    UserTable.Columns.EMAIL, email,
                    UserTable.Columns.PERMISSION, permission.value())));
            int userId = key.intValue();
            return Optional.of(new User(userId, username, password, email, permission));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String query = String.format("SELECT * FROM \"%s\"", UserTable.NAME);
        return jdbcTemplate.query(query, USER_ROW_MAPPER);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String query = String.format("SELECT * FROM \"%s\" WHERE \"%s\" = ?", UserTable.NAME, UserTable.Columns.USERNAME);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, USER_ROW_MAPPER, username));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = String.format("SELECT * FROM \"%s\" WHERE \"%s\" = ?", UserTable.NAME, UserTable.Columns.EMAIL);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, USER_ROW_MAPPER, email));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
