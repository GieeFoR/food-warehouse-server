package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.maker.MakerRepository;
import foodwarehouse.database.rowmappers.CarResultSetMapper;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.rowmappers.MakerResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.CarTable;
import foodwarehouse.database.tables.MakerTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMakerRepository implements MakerRepository {

//    private final Connection connection;
//
//    @Autowired
//    JdbcMakerRepository(DataSource dataSource) {
//        try {
//            this.connection = dataSource.getConnection();
//        }
//        catch(SQLException sqlException) {
//            throw new RestException("Cannot connect to database!");
//        }
//    }

    @Override
    public Optional<Maker> createMaker(
            Address address,
            String name,
            String phone,
            String email) {

        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("maker"), Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, address.addressId());
                statement.setString(2, name);
                statement.setString(3, phone);
                statement.setString(4, email);

                statement.executeUpdate();
                int makerId = statement.getGeneratedKeys().getInt(1);
                statement.close();

                return Optional.of(new Maker(makerId, address, name, phone, email));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Maker> updateMaker(
            int makerId,
            Address address,
            String name,
            String phone,
            String email) {

        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("maker"));
                statement.setString(1, name);
                statement.setString(2, phone);
                statement.setString(3, email);
                statement.setInt(4, makerId);

                statement.executeUpdate();
                statement.close();

                return Optional.of(new Maker(makerId, address, name, phone, email));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteMaker(int makerId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("maker"));
                statement.setInt(1, makerId);

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
    public Optional<Maker> findMakerById(int makerId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("maker_byId"));
                statement.setInt(1, makerId);

                ResultSet resultSet = statement.executeQuery();
                Maker maker = null;
                if(resultSet.next()) {
                    maker = new MakerResultSetMapper().resultSetMap(resultSet, "");
                }
                statement.close();

                return Optional.ofNullable(maker);
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Maker> findMakers() {
        List<Maker> makers = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("maker"));

                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    makers.add(new MakerResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            makers = null;
        }
        return makers;
    }
}
