package foodwarehouse.web.controller;

import foodwarehouse.core.service.CarService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.EmployeeService;
import foodwarehouse.core.service.UserService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.response.CarResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;
    private final ConnectionService connectionService;

    public CarController(
            CarService carService,
            ConnectionService connectionService) {
        this.carService = carService;
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
    public SuccessResponse<CarResponse> createCar() {
        return null;
    }
}
