package foodwarehouse.core.data.car;

import foodwarehouse.core.data.employee.Employee;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CarRepository {

    Optional<Car> createCar(
            Employee employee,
            String brand,
            String model,
            int yearOfProd,
            String regNo,
            Date insurance,
            Date inspection);

    Optional<Car> updateCar(
            int carId,
            Employee employee,
            String brand,
            String model,
            int yearOfProd,
            String regNo,
            Date insurance,
            Date inspection);

    boolean deleteCar(int carId);

    Optional<Car> findCarById(int carId);

    List<Car> findCars();
}
