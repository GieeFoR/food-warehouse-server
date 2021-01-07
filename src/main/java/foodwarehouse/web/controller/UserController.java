package foodwarehouse.web.controller;

import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.UserService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.response.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;
    private final ConnectionService connectionService;

    public UserController(UserService userService, ConnectionService connectionService) {
        this.userService = userService;
        this.connectionService = connectionService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<UserResponse>> getUsers() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var users = userService
                .findAllUsers()
                .stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
        return new SuccessResponse<>(users);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<UserResponse> getUserById(@PathVariable Integer id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return userService
                .findUserById(id)
                .map(UserResponse::fromUser)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find user with this ID."));
    }
}
