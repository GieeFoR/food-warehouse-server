package foodwarehouse.web.controller.customer;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.service.AddressService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.CustomerService;
import foodwarehouse.core.service.UserService;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.CheckEmailRequest;
import foodwarehouse.web.request.CheckUsernameRequest;
import foodwarehouse.web.request.CreateCustomerRequest;
import foodwarehouse.web.response.CheckEmailResponse;
import foodwarehouse.web.response.CheckUsernameResponse;
import foodwarehouse.web.response.RegistrationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Optional;

@RestController
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
    @PostMapping("/register/username")
    public SuccessResponse<CheckUsernameResponse> checkUsername(@RequestBody CheckUsernameRequest loginUsername) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        try {
            boolean exists = userService.findUserByUsername(loginUsername.username()).isPresent();
            return new SuccessResponse<>(new CheckUsernameResponse(exists));
        }
        catch (SQLException sqlException) {
            throw new RestException("Unable to username.");
        }
    }

    //check if email is not used
    @PostMapping("/register/email")
    public SuccessResponse<CheckEmailResponse> checkEmail(@RequestBody CheckEmailRequest loginEmail) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        try {
            boolean exists = userService.findUserByEmail(loginEmail.email()).isPresent();
            return new SuccessResponse<>(new CheckEmailResponse(exists));
        }
        catch (SQLException sqlException) {
            throw new RestException("Unable to check email.");
        }
    }

    //register user function
    @RequestMapping("/register")
    public SuccessResponse<RegistrationResponse> register(@RequestBody CreateCustomerRequest createCustomerRequest) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //add user to database
        Optional<User> user = userService.createUser(
                createCustomerRequest.account().username(),
                createCustomerRequest.account().password(),
                createCustomerRequest.account().email(),
                Permission.CUSTOMER);

        //when user was not added to database
        if(user.isEmpty()) {
            //response error
            throw new RestException("Unable to create a new user.");
        }

        //add address to database
        Optional <Address> address = addressService.createAddress(
                createCustomerRequest.address().country(),
                createCustomerRequest.address().town(),
                createCustomerRequest.address().postalCode(),
                createCustomerRequest.address().buildingNumber(),
                createCustomerRequest.address().street(),
                createCustomerRequest.address().apartmentNumber());

        //when address was not added to database
        if(address.isEmpty()) {
            //delete user from database
            userService.deleteUser(user.get());
            //response error
            throw new RestException("Unable to create a new address.");

        }

        //add customer to database
        Optional <Customer> customer = customerService.createCustomer(
                user.get(),
                address.get(),
                createCustomerRequest.customerPersonalData().name(),
                createCustomerRequest.customerPersonalData().surname(),
                createCustomerRequest.customerPersonalData().firmName(),
                createCustomerRequest.customerPersonalData().phoneNumber(),
                createCustomerRequest.customerPersonalData().tax_id());

        //when customer was not added to database
        if(customer.isEmpty()) {
            //delete user from database
            userService.deleteUser(user.get());
            //delete address from database
            addressService.deleteAddress(address.get());
            //response error
            throw new RestException("Unable to create a new customer.");
        }

        //response on success
        return new SuccessResponse<>(new RegistrationResponse(true));
    }
}
