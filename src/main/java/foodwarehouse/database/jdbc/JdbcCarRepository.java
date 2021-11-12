package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.car.CarRepository;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.database.rowmappers.AddressResultSetMapper;
import foodwarehouse.database.rowmappers.CarResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.CarTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCarRepository implements CarRepository {

//    private final Connection connection;
//
//    @Autowired
//    JdbcCarRepository(DataSource dataSource) {
//        try {
//            this.connection = dataSource.getConnection();
//        }
//        catch(SQLException sqlException) {
//            throw new RestException("Cannot connect to database!");
//        }
//    }


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
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("car"), Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, employee.employeeId());
                statement.setString(2, brand);
                statement.setString(3, model);
                statement.setInt(4, yearOfProd);
                statement.setString(5, regNo);
                statement.setDate(6, new java.sql.Date(insurance.getTime()));
                statement.setDate(7, new java.sql.Date(inspection.getTime()));
                statement.executeUpdate();

                int carId = statement.getGeneratedKeys().getInt(1);
                statement.close();

                return Optional.of(new Car(carId, employee, brand, model ,yearOfProd, regNo, insurance, inspection));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
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
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("car"));
                statement.setInt(1, employee.employeeId());
                statement.setString(2, brand);
                statement.setString(3, model);
                statement.setInt(4, yearOfProd);
                statement.setString(5, regNo);
                statement.setDate(6, new java.sql.Date(insurance.getTime()));
                statement.setDate(7, new java.sql.Date(inspection.getTime()));
                statement.setInt(8, carId);

                statement.executeUpdate();
                statement.close();

                return Optional.of(new Car(carId, employee, brand, model, yearOfProd, regNo, insurance, inspection));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCar(int carId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("car"));
                statement.setInt(1, carId);

                statement.executeUpdate();
                statement.close();

                return true;
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Car> findCarById(int carId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("car_byId"));
                statement.setInt(1, carId);

                ResultSet resultSet = statement.executeQuery();
                Car car = null;
                if(resultSet.next()) {
                    car = new CarResultSetMapper().resultSetMap(resultSet, "");
                }
                statement.close();

                return Optional.ofNullable(car);
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Car> findCars() {
        List<Car> cars = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("car"));

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    cars.add(new CarResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            cars = null;
        }
        return cars;
    }
}
