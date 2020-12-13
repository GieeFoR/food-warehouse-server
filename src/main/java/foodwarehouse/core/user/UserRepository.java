package foodwarehouse.core.user;

import foodwarehouse.web.user.Account;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> createUser(String username, String email, String password, Permission permission);

    Optional<User> createCustomer(int userId, int addressId, String name, String surname, String firmName, String phoneNumber, String taxId);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
