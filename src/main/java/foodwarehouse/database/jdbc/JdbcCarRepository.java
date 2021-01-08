package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.car.CarRepository;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.database.rowmappers.CarResultSetMapper;
import foodwarehouse.database.tables.CarTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCarRepository implements CarRepository {

    private final Connection connection;

    @Autowired
    JdbcCarRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }


    @Override
    public Optional<Car> createCar(
            Employee employee,
            String brand,
            String model,
            int yearOfProd,
            String regNo,
            Date insurance,
            Date inspection) {

        try {
            CallableStatement callableStatement = connection.prepareCall(CarTable.Procedures.INSERT);
            callableStatement.setInt(1, employee.employeeId());
            callableStatement.setString(2, brand);
            callableStatement.setString(3, model);
            callableStatement.setInt(4, yearOfProd);
            callableStatement.setString(5, regNo);
            callableStatement.setDate(6, new java.sql.Date(insurance.getTime()));
            callableStatement.setDate(7, new java.sql.Date(inspection.getTime()));

            callableStatement.executeQuery();
            int carId = callableStatement.getInt(8);
            return Optional.of(new Car(carId, employee, brand, model ,yearOfProd, regNo, insurance, inspection));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Car> updateCar(
            int carId,
            Employee employee,
            String brand,
            String model,
            int yearOfProd,
            String regNo,
            Date insurance,
            Date inspection) {

        try {
            CallableStatement callableStatement = connection.prepareCall(CarTable.Procedures.UPDATE);
            callableStatement.setInt(1, carId);
            callableStatement.setInt(2, employee.employeeId());
            callableStatement.setString(3, brand);
            callableStatement.setString(4, model);
            callableStatement.setInt(5, yearOfProd);
            callableStatement.setString(6, regNo);
            callableStatement.setDate(7, new java.sql.Date(insurance.getTime()));
            callableStatement.setDate(8, new java.sql.Date(inspection.getTime()));

            callableStatement.executeQuery();

            return Optional.of(new Car(carId, employee, brand, model, yearOfProd, regNo, insurance, inspection));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCar(int carId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(CarTable.Procedures.DELETE);
            callableStatement.setInt(1, carId);

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<Car> findCarById(int carId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(CarTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, carId);

            ResultSet resultSet = callableStatement.executeQuery();
            Car car = null;
            if(resultSet.next()) {
                car = new CarResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(car);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Car> findCars() {
        List<Car> cars = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(CarTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                cars.add(new CarResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return cars;
    }
}
