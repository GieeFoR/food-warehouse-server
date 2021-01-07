package foodwarehouse.core.data.car;

import foodwarehouse.core.data.employee.Employee;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CarRepository {

    Optional<Car> createCar(Employee employee, String brand, String model, int yearOfProd, String regNo, Date insurance, Date inspection) throws SQLException;

    Optional<Car> updateCar(int carId, String brand, String model, int yearOfProd, String regNo, Date insurance, Date inspection) throws SQLException;

    boolean deleteCar(int carId) throws SQLException;

    Optional<Car> findCarById(int carId) throws SQLException;

    List<Car> findCars() throws SQLException;
}
