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
    static final String NAME = "user";

    static final class Columns {
        static final String USER_ID = "user_id";
        static final String USER_TYPE = "user_type";
        static final String EMAIL = "email";
        static final String PASSWORD = "password";
    }
}

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> new User(
            rs.getInt(UserTable.Columns.USER_ID),
            UserType.from(rs.getString(UserTable.Columns.USER_TYPE)).get(),
            rs.getString(UserTable.Columns.EMAIL),
            rs.getString(UserTable.Columns.PASSWORD));

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
    public Optional<User> createUser(UserType userType, String email, String password) {
        try {
            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(Map.of(
                    UserTable.Columns.USER_TYPE, userType.value(),
                    UserTable.Columns.EMAIL, email,
                    UserTable.Columns.PASSWORD, password)));
            int userId = key.intValue();
            return Optional.of(new User(userId, userType, email, password));
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
    public Optional<User> findByEmail(String email) {
        String query = String.format("SELECT * FROM `%s` WHERE `%s` = ?", UserTable.NAME, UserTable.Columns.EMAIL);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, USER_ROW_MAPPER, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
