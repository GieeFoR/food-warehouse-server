package foodwarehouse.database.jdbc;

import foodwarehouse.database.jdbc.repos.ConnectionRepository;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class JdbcConnectionRepository implements ConnectionRepository {

    private final Connection connection;

    @Autowired
    JdbcConnectionRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public boolean isReachable() {
        final String CHECK_SQL_QUERY = "SELECT 1";
        boolean isConnected = true;
        try {
            connection.prepareStatement(CHECK_SQL_QUERY).execute();
        } catch (SQLException exception) {
            isConnected = false;
        }

        return isConnected;
    }
}
