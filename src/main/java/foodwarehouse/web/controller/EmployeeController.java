package foodwarehouse.web.controller;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.EmployeeService;
import foodwarehouse.core.service.UserService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.employee.CreateEmployeeRequest;
import foodwarehouse.web.request.employee.UpdateEmployeeRequest;
import foodwarehouse.web.response.others.DeleteResponse;
import foodwarehouse.web.response.employee.EmployeeResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
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
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<EmployeeResponse> getEmployeeById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return employeeService
                .findEmployeeById(id)
                .map(EmployeeResponse::fromEmployee)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find user with this ID."));
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Supplier') || hasRole('Employee')")
    public SuccessResponse<EmployeeResponse> getEmployeeOwnData(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return employeeService
                .findEmployeeByUsername(authentication.getName())
                .map(EmployeeResponse::fromEmployee)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find employee."));
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
                request.createUserRequest().username(),
                request.createUserRequest().password(),
                request.createUserRequest().email(),
                Permission.from(
                        request.createUserRequest().permission())
                        .orElseThrow(() -> new RestException("Wrong permission name.")))
                .orElseThrow(() -> new RestException("Unable to create a new user."));

        return employeeService.createEmployee(
                user,
                request.createEmployeeData().name(),
                request.createEmployeeData().surname(),
                request.createEmployeeData().position(),
                request.createEmployeeData().salary())
                .map(EmployeeResponse::fromEmployee)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Unable to create a new employee."));
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<EmployeeResponse> updateEmployee(@RequestBody UpdateEmployeeRequest request) {
        //check if database is reachable
        if (!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        User user = userService.updateUser(
                request.updateUserRequest().userId(),
                request.updateUserRequest().username(),
                request.updateUserRequest().password(),
                request.updateUserRequest().email(),
                Permission.from(
                        request.updateUserRequest().permission())
                        .orElseThrow(() -> new RestException("Wrong permission name.")))
                .orElseThrow(() -> new RestException("Unable to update an user."));

        return employeeService.updateEmployee(
                request.updateEmployeeData().employeeId(),
                user,
                request.updateEmployeeData().name(),
                request.updateEmployeeData().surname(),
                request.updateEmployeeData().position(),
                request.updateEmployeeData().salary())
                .map(EmployeeResponse::fromEmployee)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Unable to update an employee."));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<DeleteResponse>> deleteEmployees(@RequestBody List<Integer> request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        List<DeleteResponse> result = new LinkedList<>();
        for(int i : request) {
            result.add(
                    new DeleteResponse(
                            employeeService.deleteEmployee(i)));
        }

        return new SuccessResponse<>(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<DeleteResponse> deleteEmployeeById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return new SuccessResponse<>(
                DeleteResponse.fromBoolean(
                employeeService.deleteEmployee(id)));
    }
}
