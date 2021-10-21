package foodwarehouse.database.jdbc;

import foodwarehouse.database.jdbc.repos.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

@Repository
public class JdbcConnectionRepository implements ConnectionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcConnectionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean isReachable() {
        final String CHECK_SQL_QUERY = "SELECT 1";
        boolean isConnected = true;
        try {
            jdbcTemplate.update(connection -> connection.prepareStatement(CHECK_SQL_QUERY));
        } catch (CannotGetJdbcConnectionException e) {
            isConnected = false;
        }

        return isConnected;
    }
}
