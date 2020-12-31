package foodwarehouse.web.controller.admin;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.data.customer.CustomerPersonalData;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeePersonalData;
import foodwarehouse.core.service.*;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.CreateCustomerRequest;
import foodwarehouse.web.request.CreateEmployeeRequest;
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
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final ConnectionService connectionService;
    private final AddressService addressService;

    public AdminController(UserService userService,
                           CustomerService customerService,
                           EmployeeService employeeService,
                           ConnectionService connectionService,
                           AddressService addressService) {
        this.userService = userService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.connectionService = connectionService;
        this.addressService = addressService;
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

        try {
            final var users = userService
                    .findAllUsers()
                    .stream()
                    .map(user -> new UserResponse(user.userId(), user.username(), user.email(), user.permission().value()))
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
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        try {
            //get user from database with {id}
            final var user = userService
                    .findUserById(Integer.parseInt(id))
                    .map(u -> new UserResponse(u.userId(), u.username(), u.email(), u.permission().value()))
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

    @GetMapping("/customer")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<CustomerResponse>> getCustomers() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        try{
            final var customers = customerService
                    .findAllCustomers()
                    .stream()
                    .map(customer -> new CustomerResponse(
                            User.toUserResponse(customer.user()),
                            Customer.toCustomerPersonalData(customer),
                            customer.address()))
                    .collect(Collectors.toList());
            return new SuccessResponse<>(customers);
        }
        catch(SQLException sqlException) {
            String exceptionMessage = "Cannot get customers.";
            throw new RestException(exceptionMessage);
        }
    }

    @PostMapping("/customer")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //add user to database
        Optional<User> user;
        try {
            user = userService.createUser(
                    request.account().username(),
                    request.account().password(),
                    request.account().email(),
                    Permission.CUSTOMER);
        } catch (SQLException sqlException) {
            throw new RestException("Unable to create a new user.");
        }

        //add address to database
        Optional <Address> address = addressService.createAddress(
                request.address().country(),
                request.address().town(),
                request.address().postalCode(),
                request.address().buildingNumber(),
                request.address().street(),
                request.address().apartmentNumber());

        //when address was not added to database
        if(address.isEmpty()) {
            //delete user from database
            userService.deleteUser(user.get());
            //response error
            throw new RestException("Unable to create a new address.");

        }

        //add customer to database
        Optional <Customer> customer = null;
        try {
            customer = customerService.createCustomer(
                    user.get(),
                    address.get(),
                    request.customerPersonalData().name(),
                    request.customerPersonalData().surname(),
                    request.customerPersonalData().firmName(),
                    request.customerPersonalData().phoneNumber(),
                    request.customerPersonalData().tax_id());
        } catch (SQLException sqlException) {
            //delete user from database
            userService.deleteUser(user.get());
            //delete address from database
            addressService.deleteAddress(address.get());
            throw new RestException("Unable to create a new customer.");
        }

        //response on success
        return new SuccessResponse<>(
                new CustomerResponse(
                        User.toUserResponse(user.get()),
                        Customer.toCustomerPersonalData(customer.get()),
                        address.get()));
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<EmployeeResponse>> getEmployees() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        try {
            final var employees = employeeService
                    .findAllEmployees()
                    .stream()
                    .map(employee -> new EmployeeResponse(
                            new UserResponse(
                                    employee.user().userId(),
                                    employee.user().username(),
                                    employee.user().email(),
                                    employee.user().permission().value()),
                            new EmployeePersonalData(
                                    employee.employeeId(),
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

    @PostMapping("/employee")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<EmployeeResponse> createEmployee(@RequestBody CreateEmployeeRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
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
        Optional<User> user;
        try {
            user = userService.createUser(
                    request.account().username(),
                    request.account().password(),
                    request.account().email(),
                    Permission.from(request.account().permission()).get());
        } catch (SQLException sqlException) {
            throw new RestException("Unable to create a new user.");
        }

        //add employee to database
        Optional <Employee> employee = null;
        try {
            employee = employeeService.createEmployee(
                    user.get(),
                    request.employeePersonalData().name(),
                    request.employeePersonalData().surname(),
                    request.employeePersonalData().position(),
                    request.employeePersonalData().salary());
        } catch (SQLException sqlException) {
            //delete user from database
            userService.deleteUser(user.get());
            throw new RestException("Unable to create a new employee.");
        }

        return new SuccessResponse<>(
                new EmployeeResponse(
                        new UserResponse(
                                employee.get().user().userId(),
                                employee.get().user().username(),
                                employee.get().user().email(),
                                employee.get().user().permission().value()),
                        new EmployeePersonalData(
                                employee.get().employeeId(),
                                employee.get().name(),
                                employee.get().surname(),
                                employee.get().position(),
                                employee.get().salary())));
    }

    @PostMapping("/employee/update")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<EmployeeResponse> updateEmployee(@RequestBody CreateEmployeeRequest request) {
        //check if database is reachable
        if (!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        //get permission from string
        Optional<Permission> permission = Permission.from(request.account().permission());

        //when permission name was incorrect
        if (permission.isEmpty()) {
            throw new RestException("Wrong permission name.");
        }

        //add user to database
        Optional<User> user = userService.updateUser(
                request.account().userId(),
                request.account().username(),
                request.account().password(),
                request.account().email(),
                Permission.from(request.account().permission()).get());

        //when user was not added to database
        if (user.isEmpty()) {
            //response error
            throw new RestException("Unable to update an user.");
        }

        //add employee to database
        Optional<Employee> employee = employeeService.updateEmployee(
                request.employeePersonalData().employeeId(),
                user.get(),
                request.employeePersonalData().name(),
                request.employeePersonalData().surname(),
                request.employeePersonalData().position(),
                request.employeePersonalData().salary());

        //when employee was not added to database
        if (employee.isEmpty()) {
            //response error
            throw new RestException("Unable to update an employee.");
        }

        return new SuccessResponse<>(
                new EmployeeResponse(
                        new UserResponse(
                                employee.get().user().userId(),
                                employee.get().user().username(),
                                employee.get().user().email(),
                                employee.get().user().permission().value()),
                        new EmployeePersonalData(
                                employee.get().employeeId(),
                                employee.get().name(),
                                employee.get().surname(),
                                employee.get().position(),
                                employee.get().salary())));
    }
}
