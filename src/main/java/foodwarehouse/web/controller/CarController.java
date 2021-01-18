package foodwarehouse.web.controller;

import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.service.CarService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.EmployeeService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.car.CreateCarRequest;
import foodwarehouse.web.request.car.UpdateCarRequest;
import foodwarehouse.web.response.others.DeleteResponse;
import foodwarehouse.web.response.car.CarResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class CarController {

    private final CarService carService;
    private final EmployeeService employeeService;
    private final ConnectionService connectionService;

    public CarController(
            CarService carService,
            EmployeeService employeeService,
            ConnectionService connectionService) {
        this.carService = carService;
        this.employeeService = employeeService;
        this.connectionService = connectionService;
    }

    @GetMapping("/vehicle")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<List<CarResponse>> getCars() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var cars = carService
                .findCars()
                .stream()
                .map(CarResponse::fromCar)
                .collect(Collectors.toList());

        return new SuccessResponse<>(cars);
    }

    @GetMapping("/supplier/vehicle")
    @PreAuthorize("hasRole('Supplier')")
    public SuccessResponse<CarResponse> getSupplierCar(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee employee = employeeService.findEmployeeByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find employee."));

        Car car = carService
                .findCars()
                .stream()
                .filter(o -> o.driver().employeeId() == employee.employeeId())
                .findFirst()
                .orElseThrow(() -> new RestException("Cannot find car."));

        return new SuccessResponse<>(CarResponse.fromCar(car));
    }

    @GetMapping("/vehicle/{id}")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<CarResponse> getCarById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return carService
                .findCarById(id)
                .map(CarResponse::fromCar)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find car with this ID."));
    }

    @PostMapping("/vehicle")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<CarResponse> createCar(@RequestBody CreateCarRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee driver = employeeService
                .findEmployeeById(request.driverId())
                .orElseThrow(() -> new RestException("Cannot find driver."));

        return carService
                .createCar(
                        driver,
                        request.brand(),
                        request.model(),
                        request.yearOfProd(),
                        request.registrationNumber(),
                        request.insuranceExp(),
                        request.inspectionExp())
                .map(CarResponse::fromCar)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a new car."));
    }

    @PutMapping("/vehicle")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<CarResponse> updateCar(@RequestBody UpdateCarRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee driver = employeeService
                .findEmployeeById(request.driverId())
                .orElseThrow(() -> new RestException("Cannot find driver."));

        return carService
                .updateCar(
                        request.carId(),
                        driver,
                        request.brand(),
                        request.model(),
                        request.yearOfProd(),
                        request.registrationNumber(),
                        request.insuranceExp(),
                        request.inspectionExp())
                .map(CarResponse::fromCar)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update car."));
    }

    @DeleteMapping("/vehicle")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<List<DeleteResponse>> deleteCars(@RequestBody List<Integer> request) {
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
                            carService.deleteCar(i)));
        }

        return new SuccessResponse<>(result);
    }

    @DeleteMapping("/vehicle/{id}")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<DeleteResponse> deleteCarById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return new SuccessResponse<>(
                new DeleteResponse(
                        carService.deleteCar(id)));
    }
}
