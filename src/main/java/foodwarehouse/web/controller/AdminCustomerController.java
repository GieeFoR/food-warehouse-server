package foodwarehouse.web.controller;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.service.AddressService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.CustomerService;
import foodwarehouse.core.service.UserService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.customer.CreateCustomerRequest;
import foodwarehouse.web.request.customer.UpdateCustomerRequest;
import foodwarehouse.web.response.customer.CustomerResponse;
import foodwarehouse.web.response.others.DeleteResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class AdminCustomerController {

    private final CustomerService customerService;
    private final UserService userService;
    private final AddressService addressService;
    private final ConnectionService connectionService;

    public AdminCustomerController(
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<CustomerResponse> getCustomerById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return customerService
                .findCustomerById(id)
                .map(CustomerResponse::fromCustomer)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find user with this ID."));
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
                        request.createUserRequest().username(),
                        request.createUserRequest().password(),
                        request.createUserRequest().email(),
                        Permission.CUSTOMER)
                .orElseThrow(() -> new RestException("Unable to create a new user."));

        //create address
        Address address = addressService.createAddress(
                        request.createAddressRequest().country(),
                        request.createAddressRequest().town(),
                        request.createAddressRequest().postalCode(),
                        request.createAddressRequest().buildingNumber(),
                        request.createAddressRequest().street(),
                        request.createAddressRequest().apartmentNumber())
                .orElseThrow(() -> new RestException("Unable to create a new address."));

        //create customer with user and address
        //return response to client with new customer
        return customerService
                .createCustomer(
                    user,
                    address,
                    request.createCustomerData().name(),
                    request.createCustomerData().surname(),
                    request.createCustomerData().firmName(),
                    request.createCustomerData().phoneNumber(),
                    request.createCustomerData().tax_id())
                .map(CustomerResponse::fromCustomer)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Unable to create a new customer."));
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<CustomerResponse> updateCustomer(@RequestBody UpdateCustomerRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //update user
        User user = userService.updateUser(
                    request.updateUserRequest().userId(),
                    request.updateUserRequest().username(),
                    request.updateUserRequest().password(),
                    request.updateUserRequest().email(),
                    Permission.CUSTOMER)
                .orElseThrow(() -> new RestException("Unable to update a user."));

        //update address
        Address address = addressService.updateAddress(
                    request.updateAddressRequest().addressId(),
                    request.updateAddressRequest().country(),
                    request.updateAddressRequest().town(),
                    request.updateAddressRequest().postalCode(),
                    request.updateAddressRequest().buildingNumber(),
                    request.updateAddressRequest().street(),
                    request.updateAddressRequest().apartmentNumber())
                .orElseThrow(() -> new RestException("Unable to update an address."));

        //update customer
        //return response to client with updated customer
        return customerService.updateCustomer(
                    request.updateCustomerData().customerId(),
                    user,
                    address,
                    request.updateCustomerData().name(),
                    request.updateCustomerData().surname(),
                    request.updateCustomerData().firmName(),
                    request.updateCustomerData().phoneNumber(),
                    request.updateCustomerData().tax_id())
                .map(CustomerResponse::fromCustomer)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Unable to update a customer."));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<DeleteResponse>> deleteCustomers(@RequestBody List<Integer> request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        List<DeleteResponse> result = new LinkedList<>();
        for(int i : request) {
            result.add(
                    new DeleteResponse(
                            customerService.deleteCustomer(i)));
        }

        return new SuccessResponse<>(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<DeleteResponse> deleteCustomerById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return new SuccessResponse<>(
                new DeleteResponse(
                        customerService.deleteCustomer(id)));
    }
}
