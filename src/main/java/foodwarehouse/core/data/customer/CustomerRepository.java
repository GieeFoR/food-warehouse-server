package foodwarehouse.core.data.customer;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.user.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) throws SQLException;

    Optional<Customer> updateCustomer(int customerId, User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) throws SQLException;

    boolean deleteCustomer(int customerId) throws SQLException;

    List<Customer> findAllCustomers() throws SQLException;

}
