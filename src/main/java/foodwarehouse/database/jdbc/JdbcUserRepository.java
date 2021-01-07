package foodwarehouse.database.jdbc;

import foodwarehouse.database.rowmappers.UserResultSetMapper;
import foodwarehouse.database.tables.UserTable;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.data.user.UserRepository;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final Connection connection;

    @Autowired
    JdbcUserRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<User> createUser(String username, String password, String email, Permission permission) {
        try {
            CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.INSERT);
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);
            callableStatement.setString(3, permission.value());
            callableStatement.setString(4, email);

            callableStatement.executeQuery();
            int userId = callableStatement.getInt(5);

            return Optional.of(new User(userId, username, password, email, permission));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> updateUser(int userId, String username, String password, String email, Permission permission) {
        try {
            CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.UPDATE);
            callableStatement.setInt(1, userId);
            callableStatement.setString(2, username);
            callableStatement.setString(3, password);
            callableStatement.setString(4, permission.value());
            callableStatement.setString(5, email);

            callableStatement.executeQuery();

            return Optional.of(new User(userId, username, password, email, permission));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.DELETE);
            callableStatement.setInt(1, userId);

            callableStatement.executeQuery();

            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.READ_ALL);
            ResultSet resultSet = callableStatement.executeQuery();

            while(resultSet.next()) {
                users.add(new UserResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch (SQLException sqlException) {
            users = null;
        }

        return users;
    }

    @Override
    public Optional<User> findUserById(int userId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, userId);

            ResultSet resultSet = callableStatement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new UserResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(user);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        try {
            CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.READ_BY_USERNAME);
            callableStatement.setString(1, username);

            ResultSet resultSet = callableStatement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new UserResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(user);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        try {
            CallableStatement callableStatement = connection.prepareCall(UserTable.Procedures.READ_BY_EMAIL);
            callableStatement.setString(1, email);

            ResultSet resultSet = callableStatement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new UserResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(user);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }
}
