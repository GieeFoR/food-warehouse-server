package foodwarehouse.core.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> createUser(UserType userType, String email, String password);

    List<User> findAll();

    Optional<User> findByEmail(String email);
}
