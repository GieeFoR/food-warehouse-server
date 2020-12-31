package foodwarehouse.database.jdbc;

import foodwarehouse.database.rowmappers.AddressResultSetMapper;
import foodwarehouse.database.rowmappers.CustomerResultSetMapper;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.rowmappers.UserResultSetMapper;
import foodwarehouse.database.tables.AddressTable;
import foodwarehouse.database.tables.CustomerTable;
import foodwarehouse.database.tables.EmployeeTable;
import foodwarehouse.database.tables.UserTable;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.data.user.UserRepository;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import foodwarehouse.core.data.address.Address;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final String procedureReadUsers = "CALL `GET_USERS`()";
    private final String procedureReadUserById = "CALL `GET_USER_BY_ID`(?)";
    private final String procedureReadUserByUsername = "CALL `GET_USER_BY_USERNAME`(?)";
    private final String procedureReadUserByEmail = "CALL `GET_USER_BY_EMAIL`(?)";

    private final Connection connection;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcUserRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
    public boolean updateUser(int userId, String username, String password, String email, Permission permission) {
        String query = String.format("UPDATE `%s` SET `%s` = ?, `%s` = ?, `%s` = ?, `%s` = ? WHERE `%s` = ?",
                UserTable.NAME,
                UserTable.Columns.USERNAME,
                UserTable.Columns.PASSWORD,
                UserTable.Columns.PERMISSION,
                UserTable.Columns.EMAIL,
                UserTable.Columns.USER_ID);

        Object[] args = new Object[] {username, password, email, permission.value(), userId};
        int update = jdbcTemplate.update(query, args);

        if(update == 1) {
            return true;
        }
        else {
            return false;
        }
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
        CallableStatement callableStatement = connection.prepareCall(procedureReadUsers);
        ResultSet resultSet = callableStatement.executeQuery();
        List<User> users = new LinkedList<>();
        while(resultSet.next()) {
            users.add(new UserResultSetMapper().resultSetMap(resultSet));
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(int userId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadUserById);
        callableStatement.setInt(1, userId);
        ResultSet resultSet = callableStatement.executeQuery();
        User user = null;
        while(resultSet.next()) {
            user = new UserResultSetMapper().resultSetMap(resultSet);
        }
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByUsername(String username) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadUserByUsername);
        callableStatement.setString(1, username);
        ResultSet resultSet = callableStatement.executeQuery();
        User user = null;
        while(resultSet.next()) {
            user = new UserResultSetMapper().resultSetMap(resultSet);
        }
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadUserByEmail);
        callableStatement.setString(1, email);
        ResultSet resultSet = callableStatement.executeQuery();
        User user = null;
        while(resultSet.next()) {
            user = new UserResultSetMapper().resultSetMap(resultSet);
        }
        Optional<User> optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }
}
