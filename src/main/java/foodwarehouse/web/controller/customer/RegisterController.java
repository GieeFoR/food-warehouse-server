package foodwarehouse.web.controller.customer;

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
import foodwarehouse.web.response.CheckEmailResponse;
import foodwarehouse.web.response.CheckUsernameResponse;
import foodwarehouse.web.response.PingResponse;
import foodwarehouse.web.response.RegistrationResponse;
import org.springframework.web.bind.annotation.*;

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
    public SuccessResponse<RegistrationResponse> register(@RequestBody CreateCustomerRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //add user to database
        Optional<User> user;
        try {
            user = userService.createUser(
                    request.account().username(),
                    request.account().password(),
                    request.account().email(),
                    Permission.CUSTOMER);
        } catch (SQLException sqlException) {
            throw new RestException("Unable to create a new user.");
        }

        //add address to database
        Optional <Address> address;
        try {
            address = addressService.createAddress(
                    request.address().country(),
                    request.address().town(),
                    request.address().postalCode(),
                    request.address().buildingNumber(),
                    request.address().street(),
                    request.address().apartmentNumber());
        } catch (SQLException sqlException) {
            //delete user from database
            userService.deleteUser(user.get());
            //response error
            throw new RestException("Unable to create a new address.");
        }

        //add customer to database
        Optional <Customer> customer;
        try {
            customer = customerService.createCustomer(
                    user.get(),
                    address.get(),
                    request.customerPersonalData().name(),
                    request.customerPersonalData().surname(),
                    request.customerPersonalData().firmName(),
                    request.customerPersonalData().phoneNumber(),
                    request.customerPersonalData().tax_id());
        } catch (SQLException sqlException) {
            //delete user from database
            userService.deleteUser(user.get());
            //delete address from database
            addressService.deleteAddress(address.get());
            throw new RestException("Unable to create a new customer.");
        }

        //response on success
        return new SuccessResponse<>(new RegistrationResponse(true));
    }
}
