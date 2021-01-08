package foodwarehouse.web.controller;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.service.CarService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.EmployeeService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.create.CreateCarRequest;
import foodwarehouse.web.request.update.UpdateCarRequest;
import foodwarehouse.web.response.DeleteResponse;
import foodwarehouse.web.response.car.CarResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicle")
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

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
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

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<CarResponse> createCar(@RequestBody CreateCarRequest createCarRequest) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee driver = employeeService
                .findEmployeeById(createCarRequest.driverId())
                .orElseThrow(() -> new RestException("Cannot find driver."));

        return carService
                .createCar(
                        driver,
                        createCarRequest.brand(),
                        createCarRequest.model(),
                        createCarRequest.yearOfProd(),
                        createCarRequest.registrationNumber(),
                        createCarRequest.insuranceExp(),
                        createCarRequest.inspectionExp())
                .map(CarResponse::fromCar)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a new car."));
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<CarResponse> updateCar(@RequestBody UpdateCarRequest updateCarRequest) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Employee driver = employeeService
                .findEmployeeById(updateCarRequest.driverId())
                .orElseThrow(() -> new RestException("Cannot find driver."));

        return carService
                .updateCar(
                        updateCarRequest.carId(),
                        driver,
                        updateCarRequest.brand(),
                        updateCarRequest.model(),
                        updateCarRequest.yearOfProd(),
                        updateCarRequest.registrationNumber(),
                        updateCarRequest.insuranceExp(),
                        updateCarRequest.inspectionExp())
                .map(CarResponse::fromCar)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update car."));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('Admin')")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
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
