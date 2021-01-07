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
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> createCustomer(User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) {
        return customerRepository.createCustomer(user, address, name, surname, firmName, phoneNumber, taxId);
    }

    public Optional<Customer> updateCustomer(int customerId, User user, Address address, String name, String surname, String firmName, String phoneNumber, String taxId) {
        return customerRepository.updateCustomer(customerId, user, address, name, surname, firmName, phoneNumber, taxId);
    }

    public boolean deleteCustomer(int customerId) {
        return customerRepository.deleteCustomer(customerId);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAllCustomers();
    }
}
