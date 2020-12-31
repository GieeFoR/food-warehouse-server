package foodwarehouse.core.data.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> createUser(String username, String email, String password, Permission permission);

    boolean updateUser(int userId, String username, String password, String email, Permission permission);

    boolean deleteUser(User user);

    List<User> findAllUsers() throws SQLException;

    Optional<User> findUserById(int userId) throws SQLException;

    Optional<User> findUserByEmail(String email) throws SQLException;

    Optional<User> findUserByUsername(String username) throws SQLException;
}
