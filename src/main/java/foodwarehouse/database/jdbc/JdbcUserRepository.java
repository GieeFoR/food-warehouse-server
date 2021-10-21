package foodwarehouse.database.jdbc;

import foodwarehouse.database.rowmappers.UserResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.UserTable;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.data.user.UserRepository;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
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
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("user"), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, permission.value());
            statement.setString(4, email);

            statement.executeUpdate();
            int userId = statement.getGeneratedKeys().getInt(1);

            return Optional.of(new User(userId, username, password, email, permission));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> updateUser(int userId, String username, String password, String email, Permission permission) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("user"));
            statement.setString(1, username);
            statement.setString(2, permission.value());
            statement.setString(3, email);
            statement.setInt(4, userId);
            statement.setString(5, password.equals("") ? null : password);
            statement.setInt(6, userId);

            statement.executeUpdate();

            return Optional.of(new User(userId, username, password, email, permission));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("user"));
            statement.setInt(1, userId);

            statement.executeUpdate();

            return true;
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return false;
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("user"));
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                users.add(new UserResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch (SQLException | FileNotFoundException sqlException) {
            users = null;
        }

        return users;
    }

    @Override
    public Optional<User> findUserById(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("user_byId"));
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new UserResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(user);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("user_byUsername"));
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new UserResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(user);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("user_byEmail"));
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new UserResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(user);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }
}
