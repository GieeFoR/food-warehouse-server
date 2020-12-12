package foodwarehouse.core.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> createUser(String username, String email, String password, Permission permission);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
