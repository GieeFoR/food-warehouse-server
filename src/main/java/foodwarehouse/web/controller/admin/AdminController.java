package foodwarehouse.web.controller.admin;

import foodwarehouse.core.address.Address;
import foodwarehouse.core.user.Permission;
import foodwarehouse.core.user.User;
import foodwarehouse.core.user.UserService;
import foodwarehouse.core.user.customer.CustomerPersonalData;
import foodwarehouse.core.user.employee.Employee;
import foodwarehouse.core.user.employee.EmployeePersonalData;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.EmployeeRequest;
import foodwarehouse.web.response.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
        //check if database is reachable
        if(!userService.checkConnection()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        try {
            final var users = userService
                    .getUsers()
                    .stream()
                    .map(user -> new UserResponse(user.permission().value(), user.userId(), user.username(), user.email()))
                    .collect(Collectors.toList());
            return new SuccessResponse<>(users);
        }
        catch(SQLException sqlException) {
            String exceptionMessage = "Cannot get users";
            throw new RestException(exceptionMessage);
        }
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<UserResponse> getUserById(@PathVariable String id) {
        //check if database is reachable
        if(!userService.checkConnection()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        try {
            //get user from database with {id}
            final var user = userService
                    .getUserById(Integer.parseInt(id))
                    .map(u -> new UserResponse(u.permission().value(), u.userId(), u.username(), u.email()))
                    .orElseThrow(() -> new RestException("There is no user with this ID."));

            return new SuccessResponse<>(user);
        }
        catch (NumberFormatException numException) {
            //when {id} is not a digit
            throw new RestException("Wrong ID.");
        }
        catch(SQLException sqlException) {
            String exceptionMessage = "Cannot get user with ID.";
            throw new RestException(exceptionMessage);
        }
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<EmployeeResponse>> getEmployees() {
        //check if database is reachable
        if(!userService.checkConnection()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        try {
            final var employees = userService
                    .getEmployees()
                    .stream()
                    .map(employee -> new EmployeeResponse(
                            new UserResponse(
                                    employee.user().permission().value(),
                                    employee.user().userId(),
                                    employee.user().username(),
                                    employee.user().email()),
                            new EmployeePersonalData(
                                    employee.name(),
                                    employee.surname(),
                                    employee.position(),
                                    employee.salary())))
                    .collect(Collectors.toList());

            return new SuccessResponse<>(employees);
        }
        catch (SQLException sqlException){
            String exceptionMessage = "Cannot get employees.";
            throw new RestException(exceptionMessage);
        }
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<CustomerResponse>> getCustomers() {
        //check if database is reachable
        if(!userService.checkConnection()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        try{
            final var customers = userService
                    .getCustomers()
                    .stream()
                    .map(customer -> new CustomerResponse(
                            new UserResponse(
                                    customer.user().permission().value(),
                                    customer.user().userId(),
                                    customer.user().username(),
                                    customer.user().email()),
                            new CustomerPersonalData(
                                    customer.name(),
                                    customer.surname(),
                                    customer.phoneNumber(),
                                    customer.firmName(),
                                    customer.taxId()),
                            new Address(
                                    customer.address().addressId(),
                                    customer.address().country(),
                                    customer.address().town(),
                                    customer.address().postalCode(),
                                    customer.address().buildingNumber(),
                                    customer.address().street(),
                                    customer.address().apartmentNumber())))
                    .collect(Collectors.toList());
            return new SuccessResponse<>(customers);
        }
        catch(SQLException sqlException) {
            String exceptionMessage = "Cannot get customers.";
            throw new RestException(exceptionMessage);
        }
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<UserResponse> createEmployee(@RequestBody EmployeeRequest request) {
        //check if database is reachable
        if(!userService.checkConnection()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //get permission from string
        Optional<Permission> permission = Permission.from(request.account().permission());

        //when permission name was incorrect
        if(permission.isEmpty()) {
            throw new RestException("Wrong permission name.");
        }

        //add user to database
        Optional<User> user = userService.createUser(
                request.account().username(),
                request.account().password(),
                request.account().email(),
                Permission.from(request.account().permission()).get());

        //when user was not added to database
        if(user.isEmpty()) {
            //response error
            throw new RestException("Unable to create a new user.");
        }

        //add employee to database
        Optional <Employee> employee = userService.createEmployee(
                user.get(),
                request.employeePersonalData().name(),
                request.employeePersonalData().surname(),
                request.employeePersonalData().position(),
                request.employeePersonalData().salary());

        //when employee was not added to database
        if(employee.isEmpty()) {
            //delete user from database
            userService.deleteUser(user.get());
            //response error
            throw new RestException("Unable to create a new employee.");
        }

        return new SuccessResponse<>(
                new UserResponse(
                        employee.get().user().permission().value(),
                        employee.get().user().userId(),
                        employee.get().user().username(),
                        employee.get().user().email()));
    }

//    @PostMapping("/employee/update")
//    @PreAuthorize("hasRole('Admin')")
//    public SuccessResponse<UserResponse> updateEmployee(@RequestBody EmployeeRequest request) {
//        //check if database is reachable
//        if(!userService.checkConnection()) {
//            String exceptionMessage = "Cannot connect to database.";
//            System.out.println(exceptionMessage);
//            throw new DatabaseException(exceptionMessage);
//        }
//
//        System.out.println("sth");
//
//        //get permission from string
//        Optional<Permission> permission = Permission.from(request.account().permission());
//
//        //when permission name was incorrect
//        if(permission.isEmpty()) {
//            throw new RestException("Wrong permission name.");
//        }
//
//        //add user to database
//        Optional<User> user = userService.updateUser(
//                request.account().userId(),
//                request.account().username(),
//                request.account().password(),
//                request.account().email(),
//                Permission.from(request.account().permission()).get());
//
//        //when user was not added to database
//        if(user.isEmpty()) {
//            //response error
//            throw new RestException("Unable to update an user.");
//        }
//
//        //add employee to database
//        Optional <Employee> employee = userService.updateEmployee(
//                user.get(),
//                request.employeePersonalData().name(),
//                request.employeePersonalData().surname(),
//                request.employeePersonalData().position(),
//                request.employeePersonalData().salary());
//
//        //when employee was not added to database
//        if(employee.isEmpty()) {
//            //delete user from database
//            userService.deleteUser(user.get());
//            //response error
//            throw new RestException("Unable to update an employee.");
//        }
//
//        return null;
////        return new SuccessResponse<>(
////                new UserResponse(
////                        employee.get().user().permission().value(),
////                        employee.get().user().userId(),
////                        employee.get().user().username(),
////                        employee.get().user().email()));
//    }
}
