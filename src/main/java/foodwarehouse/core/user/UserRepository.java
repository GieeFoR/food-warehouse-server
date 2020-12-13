package foodwarehouse.core.user;

import foodwarehouse.core.user.customer.Customer;
import foodwarehouse.web.user.Account;
import universitymanagement.core.common.Address;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> createUser(String username, String email, String password, Permission permission);

    Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
