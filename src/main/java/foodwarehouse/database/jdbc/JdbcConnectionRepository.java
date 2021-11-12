package foodwarehouse.database.jdbc;

import foodwarehouse.database.jdbc.repos.ConnectionRepository;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.*;

@Repository
public class JdbcConnectionRepository implements ConnectionRepository {

//    private final Connection connection;
//
//    @Autowired
//    JdbcConnectionRepository(DataSource dataSource) {
//        try {
//            this.connection = dataSource.getConnection();
//        }
//        catch(SQLException sqlException) {
//            throw new RestException("Cannot connect to database!");
//        }
//    }

    @Override
    public boolean isReachable() {
        final String CHECK_SQL_QUERY = "SELECT 1";
        boolean isConnected = true;
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                connection.prepareStatement(CHECK_SQL_QUERY).execute();
            }
        } catch (SQLException e) {
            isConnected = false;
        }
        return isConnected;
    }
}
