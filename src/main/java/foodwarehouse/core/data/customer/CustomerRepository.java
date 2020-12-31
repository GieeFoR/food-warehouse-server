package foodwarehouse.core.data.customer;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.user.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId);

    boolean updateCustomer(Customer customer, User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId);

    boolean deleteCustomer(Customer customer);

    List<Customer> findAllCustomers() throws SQLException;

}
