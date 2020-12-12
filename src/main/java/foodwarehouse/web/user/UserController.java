package foodwarehouse.web.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import foodwarehouse.core.user.UserService;
import foodwarehouse.core.user.Permission;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.RestException;

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
        System.out.println(loginUsername.username());
        boolean exists = userService.findByUsername(loginUsername.username()).isPresent();
        System.out.println(exists);
        return new SuccessResponse<>(new CheckUsernameResponse(exists));
    }

    @PostMapping("/register/email")
    public SuccessResponse<CheckEmailResponse> checkEmail(@RequestBody CheckEmailRequest loginEmail) {
        System.out.println(loginEmail.email());
        boolean exists = userService.findByEmail(loginEmail.email()).isPresent();
        System.out.println(exists);
        return new SuccessResponse<>(new CheckEmailResponse(exists));
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