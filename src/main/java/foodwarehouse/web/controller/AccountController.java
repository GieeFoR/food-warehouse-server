package foodwarehouse.web.controller;


import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.service.*;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.customer.UpdateCustomerByCustomerRequest;
import foodwarehouse.web.request.customer.UpdateCustomerRequest;
import foodwarehouse.web.request.user.UpdateUserRequest;
import foodwarehouse.web.response.account.NameResponse;
import foodwarehouse.web.response.address.AddressResponse;
import foodwarehouse.web.response.customer.CustomerResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AddressService addressService;
    private final CustomerService customerService;
    private final ConnectionService connectionService;
    private final EmployeeService employeeService;
    private final UserService userService;

    public AccountController(AddressService addressService, CustomerService customerService, ConnectionService connectionService, EmployeeService employeeService, UserService userService) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.connectionService = connectionService;
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping("/address")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<List<AddressResponse>> getAddress(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Customer customer = customerService
                .findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find user."));

        List<Address> addressList = new LinkedList<>();
        addressList.add(customer.address());

        return new SuccessResponse<>(addressList.stream().map(AddressResponse::fromAddress).collect(Collectors.toList()));
    }

    @GetMapping("/name")
    @PreAuthorize("hasRole('Customer') || hasRole('Admin') || hasRole('Manager') || hasRole('Employee') || hasRole('Supplier')")
    public SuccessResponse<NameResponse> getName(HttpServletRequest request, Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        if(request.isUserInRole("Customer")) {
            return customerService
                    .findCustomerByUsername(authentication.getName())
                    .map(NameResponse::fromCustomer)
                    .map(SuccessResponse::new)
                    .orElseThrow(() -> new RestException("Cannot find user."));
        }
        else {
            return employeeService
                    .findEmployeeByUsername(authentication.getName())
                    .map(NameResponse::fromEmployee)
                    .map(SuccessResponse::new)
                    .orElseThrow(() -> new RestException("Cannot find user."));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<CustomerResponse> getCustomer(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return customerService
                .findCustomerByUsername(authentication.getName())
                .map(CustomerResponse::fromCustomer)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find customer."));
    }

    @PostMapping("/settings")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<CustomerResponse> updateCustomer(Authentication authentication, @RequestBody UpdateCustomerByCustomerRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Customer customer = customerService.findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find customer."));

        //update user
        User user = userService.updateUser(
                customer.user().userId(),
                request.updateUserByCustomerRequest().username(),
                request.updateUserByCustomerRequest().password(),
                request.updateUserByCustomerRequest().email(),
                Permission.CUSTOMER)
                .orElseThrow(() -> new RestException("Unable to update a user."));

        //update address
        Address address = addressService.updateAddress(
                customer.address().addressId(),
                request.updateAddressByCustomerRequest().country(),
                request.updateAddressByCustomerRequest().town(),
                request.updateAddressByCustomerRequest().postalCode(),
                request.updateAddressByCustomerRequest().buildingNumber(),
                request.updateAddressByCustomerRequest().street(),
                request.updateAddressByCustomerRequest().apartmentNumber())
                .orElseThrow(() -> new RestException("Unable to update an address."));

        //update customer
        //return response to client with updated customer
        return customerService.updateCustomer(
                customer.customerId(),
                user,
                address,
                request.updateCustomerByCustomerData().name(),
                request.updateCustomerByCustomerData().surname(),
                request.updateCustomerByCustomerData().firmName(),
                request.updateCustomerByCustomerData().phoneNumber(),
                request.updateCustomerByCustomerData().tax_id(),
                customer.discount())
                .map(CustomerResponse::fromCustomer)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Unable to update a customer."));
    }
}
