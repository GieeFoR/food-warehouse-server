package foodwarehouse.web.controller.admin;

import foodwarehouse.core.user.Permission;
import foodwarehouse.core.user.UserService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.CreateUserRequest;
import foodwarehouse.web.response.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<UserResponse>> getUsers() {
        final var users = userService
                .getUsers()
                .stream()
                .map(user -> new UserResponse(user.permission().value(), user.userId(), user.username()))
                .collect(Collectors.toList());
        return new SuccessResponse<>(users);
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('Admin')")
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
