package foodwarehouse.web.user;

import foodwarehouse.core.user.User;
import foodwarehouse.core.user.customer.Customer;
import foodwarehouse.web.request.CheckEmailRequest;
import foodwarehouse.web.request.CheckUsernameRequest;
import foodwarehouse.web.request.CreateCustomerRequest;
import foodwarehouse.web.request.CreateUserRequest;
import foodwarehouse.web.response.CheckEmailResponse;
import foodwarehouse.web.response.CheckUsernameResponse;
import foodwarehouse.web.response.RegistrationResponse;
import foodwarehouse.web.response.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import foodwarehouse.core.user.UserService;
import foodwarehouse.core.user.Permission;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.RestException;
import foodwarehouse.core.address.Address;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register/username")
    public SuccessResponse<CheckUsernameResponse> checkUsername(@RequestBody CheckUsernameRequest loginUsername) {
        boolean exists = userService.findByUsername(loginUsername.username()).isPresent();
        return new SuccessResponse<>(new CheckUsernameResponse(exists));
    }

    @PostMapping("/register/email")
    public SuccessResponse<CheckEmailResponse> checkEmail(@RequestBody CheckEmailRequest loginEmail) {
        boolean exists = userService.findByEmail(loginEmail.email()).isPresent();
        return new SuccessResponse<>(new CheckEmailResponse(exists));
    }

    @PostMapping("/register")
    public SuccessResponse<RegistrationResponse> register(@RequestBody CreateCustomerRequest createCustomerRequest) {


        Optional <User> user = userService.createUser(
                createCustomerRequest.account().username(),
                createCustomerRequest.account().password(),
                createCustomerRequest.account().email(),
                Permission.CUSTOMER);

        if(user.isEmpty()) {
            throw new RestException("Unable to create a new user.");
        }

        Optional <Address> address = userService.createAddress(
                createCustomerRequest.address().country(),
                createCustomerRequest.address().town(),
                createCustomerRequest.address().postalCode(),
                createCustomerRequest.address().buildingNumber(),
                createCustomerRequest.address().street(),
                createCustomerRequest.address().apartmentNumber());

        if(address.isEmpty()) {
            throw new RestException("Unable to create a new user.");
            //delete user from database
        }

        Optional <Customer> customer = userService.createCustomer(
                user.get(),
                address.get(),
                createCustomerRequest.personalData().name(),
                createCustomerRequest.personalData().surname(),
                createCustomerRequest.personalData().firmName(),
                createCustomerRequest.personalData().phoneNumber(),
                createCustomerRequest.personalData().tax_id());

        if(customer.isEmpty()) {
            throw new RestException("Unable to create a new user.");
            //delete user and address from database
        }
        return new SuccessResponse<>(new RegistrationResponse(true));
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/users")
    public SuccessResponse<List<UserResponse>> getUsers() {
        final var users = userService
                .getUsers()
                .stream()
                .map(user -> new UserResponse(user.permission().value(), user.userId(), user.username()))
                .collect(Collectors.toList());
        return new SuccessResponse<>(users);
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/users")
    public SuccessResponse<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        Optional<Permission> permission = Permission.from(request.account().permission());

        if(permission.isEmpty()) {
            throw new RestException("Wrong permission name.");
        }
        else {
            return userService
                    .createUser(request.account().username(), request.account().password(), request.account().email(), Permission.from(request.account().permission()).get())
                    .map(user -> new SuccessResponse<>(new UserResponse(user.permission().value(), user.userId(), user.username())))
                    .orElseThrow(() -> new RestException("Unable to create a new user."));
        }
    }
}