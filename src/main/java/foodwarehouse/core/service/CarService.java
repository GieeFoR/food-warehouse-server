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
public class CarService implements CarRepository {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Optional<Car> createCar(Employee employee, String brand, String model, int yearOfProd, String regNo, Date insurance, Date inspection) throws SQLException {
        return carRepository.createCar(employee, brand, model, yearOfProd, regNo, insurance, inspection);
    }

    @Override
    public Optional<Car> updateCar(int carId, String brand, String model, int yearOfProd, String regNo, Date insurance, Date inspection) throws SQLException {
        return carRepository.updateCar(carId, brand, model, yearOfProd, regNo, insurance, inspection);
    }

    @Override
    public boolean deleteCar(int carId) throws SQLException {
        return carRepository.deleteCar(carId);
    }

    @Override
    public Optional<Car> findCarById(int carId) throws SQLException {
        return carRepository.findCarById(carId);
    }

    @Override
    public List<Car> findCars() throws SQLException {
        return carRepository.findCars();
    }
}
