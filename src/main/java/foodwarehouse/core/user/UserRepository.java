package foodwarehouse.core.user;

import foodwarehouse.core.user.customer.Customer;
import foodwarehouse.core.address.Address;
import foodwarehouse.core.user.employee.Employee;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> createUser(String username, String email, String password, Permission permission);

    Optional<Address> createAddress(String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber);

    Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId);

    Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary);

    boolean updateUser(int userId, String username, String password, String email, Permission permission);

    boolean updateAddress(Address address, String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber);

    boolean updateCustomer(Customer customer, User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId);

    boolean updateEmployee(Employee employee, User user, String name, String surname, String position, Float salary);

    boolean deleteUser(User user);

    boolean deleteAddress(Address address);

    boolean deleteCustomer(Customer customer);

    boolean checkConnection();

    List<User> findAllUsers();
    Optional<User> findUserById(int userId);

    List<Employee> findAllEmployees();

    List<Customer> findAllCustomers();

    Optional<Address> findAddressById(int addressId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);
}
