package foodwarehouse.core.service;

import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.car.CarRepository;
import foodwarehouse.core.data.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<Car> createCar(
            Employee employee,
            String brand,
            String model,
            int yearOfProd,
            String regNo,
            Date insurance,
            Date inspection) {
        return carRepository.createCar(
                employee,
                brand,
                model,
                yearOfProd,
                regNo,
                insurance,
                inspection);
    }

    public Optional<Car> updateCar(
            int carId,
            Employee employee,
            String brand,
            String model,
            int yearOfProd,
            String regNo,
            Date insurance,
            Date inspection) {
        return carRepository.updateCar(
                carId,
                employee,
                brand,
                model,
                yearOfProd,
                regNo,
                insurance,
                inspection);
    }

    public boolean deleteCar(int carId) {
        return carRepository.deleteCar(carId);
    }

    public Optional<Car> findCarById(int carId) {
        return carRepository.findCarById(carId);
    }

    public List<Car> findCars() {
        return carRepository.findCars();
    }
}
