package foodwarehouse.core.service;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.customer.CustomerRepository;
import foodwarehouse.core.data.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CustomerRepository {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) throws SQLException {
        return customerRepository.createCustomer(user, address, name, surname, firmName, phoneNumber, taxId);
    }

    @Override
    public Optional<Customer> updateCustomer(int customerId, User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) throws SQLException {
        return customerRepository.updateCustomer(customerId, user, address, name, surname, firmName, phoneNumber, taxId);
    }

    @Override
    public boolean deleteCustomer(int customerId) throws SQLException {
        return customerRepository.deleteCustomer(customerId);
    }

    @Override
    public List<Customer> findAllCustomers() throws SQLException {
        return customerRepository.findAllCustomers();
    }
}
