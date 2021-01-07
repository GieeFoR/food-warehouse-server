package foodwarehouse.web.controller;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.user.Account;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.service.AddressService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.CustomerService;
import foodwarehouse.core.service.UserService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.CreateCustomerRequest;
import foodwarehouse.web.response.AddressResponse;
import foodwarehouse.web.response.CustomerResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;
    private final AddressService addressService;
    private final ConnectionService connectionService;

    public CustomerController(
            CustomerService customerService,
            UserService userService,
            AddressService addressService,
            ConnectionService connectionService) {
        this.customerService = customerService;
        this.userService = userService;
        this.addressService = addressService;
        this.connectionService = connectionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<CustomerResponse>> getCustomers() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var customers = customerService
                .findAllCustomers()
                .stream()
                .map(CustomerResponse::fromCustomer)
                .collect(Collectors.toList());

        return new SuccessResponse<>(customers);
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //create user
        User user = userService.createUser(
                        request.account().username(),
                        request.account().password(),
                        request.account().email(),
                        Permission.CUSTOMER)
                .orElseThrow(() -> new RestException("Unable to create a new user."));

        //create address
        Address address = addressService.createAddress(
                        request.addressResponse().country(),
                        request.addressResponse().town(),
                        request.addressResponse().postalCode(),
                        request.addressResponse().buildingNumber(),
                        request.addressResponse().street(),
                        request.addressResponse().apartmentNumber())
                .orElseThrow(() -> new RestException("Unable to create a new address."));

        //create customer with user and address
        //return response to client with new customer
        return customerService
                .createCustomer(
                    user,
                    address,
                    request.customerPersonalData().name(),
                    request.customerPersonalData().surname(),
                    request.customerPersonalData().firmName(),
                    request.customerPersonalData().phoneNumber(),
                    request.customerPersonalData().tax_id())
                .map(CustomerResponse::fromCustomer)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Unable to create a new customer."));
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<CustomerResponse> updateCustomer(@RequestBody CreateCustomerRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //update user
        User user = userService.updateUser(
                    request.account().userId(),
                    request.account().username(),
                    request.account().password(),
                    request.account().email(),
                    Permission.CUSTOMER)
                .orElseThrow(() -> new RestException("Unable to update a user."));

        //update address
        Address address = addressService.updateAddress(
                    request.addressResponse().addressId(),
                    request.addressResponse().country(),
                    request.addressResponse().town(),
                    request.addressResponse().postalCode(),
                    request.addressResponse().buildingNumber(),
                    request.addressResponse().street(),
                    request.addressResponse().apartmentNumber())
                .orElseThrow(() -> new RestException("Unable to update an address."));

        //update customer
        //return response to client with updated customer
        return customerService.updateCustomer(
                    request.customerPersonalData().customerId(),
                    user,
                    address,
                    request.customerPersonalData().name(),
                    request.customerPersonalData().surname(),
                    request.customerPersonalData().firmName(),
                    request.customerPersonalData().phoneNumber(),
                    request.customerPersonalData().tax_id())
                .map(CustomerResponse::fromCustomer)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Unable to update a customer."));
    }
}
