package foodwarehouse.core.user;

import foodwarehouse.core.user.customer.Customer;
import foodwarehouse.core.address.Address;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> createUser(String username, String email, String password, Permission permission);

    Optional<Address> createAddress(String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber);

    Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId);

    boolean deleteUser(User user);

    boolean deleteAddress(Address address);

    boolean deleteCustomer(Customer customer);

    boolean checkConnection();

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
