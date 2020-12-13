package foodwarehouse.web.controller.admin;

import foodwarehouse.core.address.Address;
import foodwarehouse.core.user.Account;
import foodwarehouse.core.user.Permission;
import foodwarehouse.core.user.User;
import foodwarehouse.core.user.UserService;
import foodwarehouse.core.user.customer.CustomerPersonalData;
import foodwarehouse.core.user.employee.Employee;
import foodwarehouse.core.user.employee.EmployeePersonalData;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.CreateUserRequest;
import foodwarehouse.web.response.CustomerResponse;
import foodwarehouse.web.response.EmployeeResponse;
import foodwarehouse.web.response.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
                .map(user -> new UserResponse(user.permission().value(), user.userId(), user.username(), user.email()))
                .collect(Collectors.toList());
        return new SuccessResponse<>(users);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<UserResponse> getUserById(@PathVariable String id) {
        try {
            final var user = userService
                    .getUserById(Integer.parseInt(id))
                    .map(u -> new UserResponse(u.permission().value(), u.userId(), u.username(), u.email()))
                    .orElseThrow(() -> new RestException("There is no user with this id."));

            return new SuccessResponse<>(user);
        }
        catch (NumberFormatException numException) {
            throw new RestException("There is no user with this id.");
        }
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<EmployeeResponse>> getEmployees() {
        final var employees = userService
                .getEmployees()
                .stream()
                .map(employee -> new EmployeeResponse(
                        new UserResponse(employee.user().permission().value(), employee.user().userId(), employee.user().username(), employee.user().email()),
                        new EmployeePersonalData(employee.name(), employee.surname(), employee.position(), employee.salary())))
                .collect(Collectors.toList());

            for(EmployeeResponse e: employees) {
                System.out.println(e);
            }
        return new SuccessResponse<>(employees);
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<CustomerResponse>> getCustomers() {
        final var customers = userService
                .getCustomers()
                .stream()
                .map(customer -> new CustomerResponse(
                        new UserResponse(customer.user().permission().value(), customer.user().userId(), customer.user().username(), customer.user().email()),
                        new CustomerPersonalData(customer.name(), customer.surname(), customer.phoneNumber(), customer.firmName(), customer.taxId()),
                        new Address(customer.address().addressId(), customer.address().country(), customer.address().town(), customer.address().postalCode(), customer.address().buildingNumber(),
                                customer.address().street(), customer.address().apartmentNumber())))
                .collect(Collectors.toList());

        for(CustomerResponse e: customers) {
            System.out.println(e);
        }
        return new SuccessResponse<>(customers);
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        Optional<Permission> permission = Permission.from(request.account().permission());

        if(permission.isEmpty()) {
            throw new RestException("Wrong permission name.");
        }
        else {
            return userService
                    .createUser(request.account().username(), request.account().password(), request.account().email(), Permission.from(request.account().permission()).get())
                    .map(user -> new SuccessResponse<>(new UserResponse(user.permission().value(), user.userId(), user.username(), user.email())))
                    .orElseThrow(() -> new RestException("Unable to create a new user."));
        }
    }
}
