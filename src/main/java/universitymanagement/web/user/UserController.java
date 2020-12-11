package universitymanagement.web.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import universitymanagement.core.user.UserService;
import universitymanagement.core.user.UserType;
import universitymanagement.web.common.SuccessResponse;
import universitymanagement.web.error.RestException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public SuccessResponse<List<UserResponse>> getUsers() {
        final var users = userService
                .getUsers()
                .stream()
                .map(user -> new UserResponse(user.userType().value(), user.userId(), user.email()))
                .collect(Collectors.toList());
        return new SuccessResponse<>(users);
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping
    public SuccessResponse<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        return userService
                .createUser(UserType.ADMIN, request.email(), request.password())
                .map(user -> new SuccessResponse<>(new UserResponse(user.userType().value(), user.userId(), user.email())))
                .orElseThrow(() -> new RestException("Unable to create a user."));
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/employees")
    public SuccessResponse<UserResponse> createEmployee(@RequestBody CreateUserRequest request) {
        return userService
                .createUser(UserType.EMPLOYEE, request.email(), request.password())
                .map(user -> new SuccessResponse<>(new UserResponse(user.userType().value(), user.userId(), user.email())))
                .orElseThrow(() -> new RestException("Unable to create a user."));
    }
}