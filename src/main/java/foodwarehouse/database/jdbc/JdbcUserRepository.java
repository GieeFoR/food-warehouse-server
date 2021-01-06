package foodwarehouse.database.jdbc;

import foodwarehouse.database.rowmappers.UserResultSetMapper;
import foodwarehouse.database.tables.UserTable;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final Connection connection;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcUserRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> createUser(String username, String password, String email, Permission permission) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.INSERT);
        callableStatement.setString(1, username);
        callableStatement.setString(2, password);
        callableStatement.setString(3, permission.value());
        callableStatement.setString(4, email);

        callableStatement.executeQuery();
        int userId = callableStatement.getInt(5);

        return Optional.of(new User(userId, username, password, email, permission));
    }

    @Override
    public Optional<User> updateUser(int userId, String username, String password, String email, Permission permission) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.UPDATE);
        callableStatement.setInt(1, userId);
        callableStatement.setString(2, username);
        callableStatement.setString(3, password);
        callableStatement.setString(4, permission.value());
        callableStatement.setString(5, email);

        callableStatement.executeQuery();

        return Optional.of(new User(userId, username, password, email, permission));
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
        CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.READ_ALL);
        ResultSet resultSet = callableStatement.executeQuery();
        List<User> users = new LinkedList<>();
        while(resultSet.next()) {
            users.add(new UserResultSetMapper().resultSetMap(resultSet, ""));
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(int userId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.READ_BY_ID);
        callableStatement.setInt(1, userId);
        ResultSet resultSet = callableStatement.executeQuery();
        User user = null;
        while(resultSet.next()) {
            user = new UserResultSetMapper().resultSetMap(resultSet, "");
        }
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByUsername(String username) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.READ_BY_USERNAME);
        callableStatement.setString(1, username);
        ResultSet resultSet = callableStatement.executeQuery();
        User user = null;
        while(resultSet.next()) {
            user = new UserResultSetMapper().resultSetMap(resultSet, "");
        }
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.READ_BY_EMAIL);
        callableStatement.setString(1, email);
        ResultSet resultSet = callableStatement.executeQuery();
        User user = null;
        while(resultSet.next()) {
            user = new UserResultSetMapper().resultSetMap(resultSet, "");
        }
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }
}
