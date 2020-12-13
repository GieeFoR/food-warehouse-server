package foodwarehouse.core.user;

import foodwarehouse.core.user.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import foodwarehouse.core.address.Address;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> createUser(String username, String password, String email, Permission permission) {
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        return userRepository.createUser(username, encryptedPassword, email, permission);
    }

    public Optional<Address> createAddress(String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) {
        return userRepository.createAddress(country, town, postalCode, buildingNumber, street, apartmentNumber);
    }

    public Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) {
        return userRepository.createCustomer(user, address, name, surname, firmName, phoneNumber, taxId);
    }

    public boolean deleteUser(User user) {
        return userRepository.deleteUser(user);
    }

    public boolean deleteAddress(Address address) {
        return userRepository.deleteAddress(address);
    }

    public boolean deleteCustomer(Customer customer) {
        return userRepository.deleteCustomer(customer);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
