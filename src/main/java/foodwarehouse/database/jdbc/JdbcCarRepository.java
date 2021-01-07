package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.car.CarRepository;
import foodwarehouse.core.data.employee.Employee;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCarRepository implements CarRepository {
    @Override
    public Optional<Car> createCar(Employee employee, String brand, String model, int yearOfProd, String regNo, Date insurance, Date inspection) {
        return Optional.empty();
    }

    @Override
    public Optional<Car> updateCar(int carId, String brand, String model, int yearOfProd, String regNo, Date insurance, Date inspection) {
        return Optional.empty();
    }

    @Override
    public boolean deleteCar(int carId) {
        return false;
    }

    @Override
    public Optional<Car> findCarById(int carId) {
        return Optional.empty();
    }

    @Override
    public List<Car> findCars() {
        return null;
    }
}
