package foodwarehouse.core.data.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> createUser(String username, String email, String password, Permission permission);

    Optional<User> updateUser(int userId, String username, String password, String email, Permission permission);

    boolean deleteUser(int userId);

    List<User> findAllUsers();

    Optional<User> findUserById(int userId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);
}
