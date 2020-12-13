package foodwarehouse.web.controller.customer;

import foodwarehouse.core.address.Address;
import foodwarehouse.core.user.Permission;
import foodwarehouse.core.user.User;
import foodwarehouse.core.user.UserService;
import foodwarehouse.core.user.customer.Customer;
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

import java.util.Optional;

@RestController
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    //check if username is not used
    @PostMapping("/register/username")
    public SuccessResponse<CheckUsernameResponse> checkUsername(@RequestBody CheckUsernameRequest loginUsername) {
        boolean exists = userService.findByUsername(loginUsername.username()).isPresent();
        return new SuccessResponse<>(new CheckUsernameResponse(exists));
    }

    //check if email is not used
    @PostMapping("/register/email")
    public SuccessResponse<CheckEmailResponse> checkEmail(@RequestBody CheckEmailRequest loginEmail) {
        boolean exists = userService.findByEmail(loginEmail.email()).isPresent();
        return new SuccessResponse<>(new CheckEmailResponse(exists));
    }

    //register user function
    @RequestMapping("/register")
    public SuccessResponse<RegistrationResponse> register(@RequestBody CreateCustomerRequest createCustomerRequest) {

        if(!userService.checkConnection()) {
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
        Optional <Address> address = userService.createAddress(
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
        Optional <Customer> customer = userService.createCustomer(
                user.get(),
                address.get(),
                createCustomerRequest.personalData().name(),
                createCustomerRequest.personalData().surname(),
                createCustomerRequest.personalData().firmName(),
                createCustomerRequest.personalData().phoneNumber(),
                createCustomerRequest.personalData().tax_id());

        //when customer was not added to database
        if(customer.isEmpty()) {
            //delete user from database
            userService.deleteUser(user.get());
            //delete address from database
            userService.deleteAddress(address.get());
            //response error
            throw new RestException("Unable to create a new customer.");
        }

        //response on success
        return new SuccessResponse<>(new RegistrationResponse(true));
    }
}
