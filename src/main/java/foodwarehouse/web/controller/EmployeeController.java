package foodwarehouse.web.controller;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeePersonalData;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.EmployeeService;
import foodwarehouse.core.service.UserService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.CreateEmployeeRequest;
import foodwarehouse.web.response.CustomerResponse;
import foodwarehouse.web.response.EmployeeResponse;
import foodwarehouse.web.response.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;
    private final ConnectionService connectionService;

    public EmployeeController(EmployeeService employeeService, UserService userService, ConnectionService connectionService) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.connectionService = connectionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<EmployeeResponse>> getEmployees() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var customers = employeeService
                .findAllEmployees()
                .stream()
                .map(EmployeeResponse::fromEmployee)
                .collect(Collectors.toList());

        return new SuccessResponse<>(customers);
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<EmployeeResponse> createEmployee(@RequestBody CreateEmployeeRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        User user = userService.createUser(
                request.account().username(),
                request.account().password(),
                request.account().email(),
                Permission.from(
                        request.account().permission())
                        .orElseThrow(() -> new RestException("Wrong permission name.")))
                .orElseThrow(() -> new RestException("Unable to create a new user."));

        return employeeService.createEmployee(
                user,
                request.employeePersonalData().name(),
                request.employeePersonalData().surname(),
                request.employeePersonalData().position(),
                request.employeePersonalData().salary())
                .map(EmployeeResponse::fromEmployee)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Unable to create a new employee."));
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<EmployeeResponse> updateEmployee(@RequestBody CreateEmployeeRequest request) {
        //check if database is reachable
        if (!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        User user = userService.updateUser(
                request.account().userId(),
                request.account().username(),
                request.account().password(),
                request.account().email(),
                Permission.from(
                        request.account().permission())
                        .orElseThrow(() -> new RestException("Wrong permission name.")))
                .orElseThrow(() -> new RestException("Unable to update an user."));

        return employeeService.updateEmployee(
                request.employeePersonalData().employeeId(),
                user,
                request.employeePersonalData().name(),
                request.employeePersonalData().surname(),
                request.employeePersonalData().position(),
                request.employeePersonalData().salary())
                .map(EmployeeResponse::fromEmployee)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Unable to update an employee."));
    }
}
