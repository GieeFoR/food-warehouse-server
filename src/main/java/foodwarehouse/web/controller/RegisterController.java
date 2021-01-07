package foodwarehouse.web.controller;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.service.*;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.CheckEmailRequest;
import foodwarehouse.web.request.CheckUsernameRequest;
import foodwarehouse.web.request.CreateCustomerRequest;
import foodwarehouse.web.response.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final CustomerService customerService;
    private final AddressService addressService;
    private final ConnectionService connectionService;

    public RegisterController(UserService userService,
                              CustomerService customerService,
                              AddressService addressService,
                              ConnectionService connectionService) {
        this.userService = userService;
        this.customerService = customerService;
        this.addressService = addressService;
        this.connectionService = connectionService;
    }

    //check if username is not used
    @PostMapping("/username")
    public SuccessResponse<CheckUsernameResponse> checkUsername(@RequestBody CheckUsernameRequest loginUsername) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return new SuccessResponse<>(
                new CheckUsernameResponse(
                        userService.findUserByUsername(loginUsername.username()).isPresent()));
    }

    //check if email is not used
    @PostMapping("/email")
    public SuccessResponse<CheckEmailResponse> checkEmail(@RequestBody CheckEmailRequest loginEmail) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return new SuccessResponse<>(
                new CheckEmailResponse(
                        userService.findUserByEmail(loginEmail.email()).isPresent()));
    }

    //register user function
    @PostMapping
    public SuccessResponse<RegistrationResponse> register(@RequestBody CreateCustomerRequest request) {
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
        customerService.createCustomer(
                        user,
                        address,
                        request.customerPersonalData().name(),
                        request.customerPersonalData().surname(),
                        request.customerPersonalData().firmName(),
                        request.customerPersonalData().phoneNumber(),
                        request.customerPersonalData().tax_id())
                .orElseThrow(() -> new RestException("Unable to create a new customer."));

        return new SuccessResponse<>(new RegistrationResponse(true));
    }
}
