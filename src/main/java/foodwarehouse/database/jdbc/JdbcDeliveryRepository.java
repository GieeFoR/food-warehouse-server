package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.delivery.DeliveryRepository;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.database.rowmappers.CarResultSetMapper;
import foodwarehouse.database.rowmappers.DeliveryResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.CarTable;
import foodwarehouse.database.tables.DeliveryTable;
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
public class JdbcDeliveryRepository implements DeliveryRepository {

    private final Connection connection;

    @Autowired
    JdbcDeliveryRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<Delivery> createDelivery(Address address, Employee supplier) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("deliery"), Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, address.addressId());
            statement.setInt(2, supplier.employeeId());

            statement.executeUpdate();
            int deliveryId = statement.getGeneratedKeys().getInt(1);
            return Optional.of(new Delivery(deliveryId, address, supplier, null, null));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Delivery> updateDelivery(int deliveryId, Address address, Employee supplier) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("delivery"));
            statement.setInt(1, address.addressId());
            statement.setInt(2, supplier.employeeId());
            statement.setInt(3, deliveryId);

            statement.executeUpdate();

            return Optional.of(new Delivery(deliveryId, address, supplier, null, null));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Delivery> updateRemoveDate(int deliveryId, Address address, Employee supplier, Date date) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("delivery_removeDate"));
            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setInt(2, deliveryId);

            statement.executeUpdate();

            return Optional.of(new Delivery(deliveryId, address, supplier, date, null));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Delivery> updateCompleteDate(int deliveryId, Address address, Employee supplier, Date date) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("delivery_completeDate"));
            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setInt(2, deliveryId);

            statement.executeUpdate();

            return Optional.of(new Delivery(deliveryId, address, supplier, date, null));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteDelivery(int deliveryId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(DeliveryTable.Procedures.DELETE);
            callableStatement.setInt(1, deliveryId);

            callableStatement.executeUpdate();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<Delivery> findDeliveryById(int deliveryId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("delivery_byId"));
            statement.setInt(1, deliveryId);

            ResultSet resultSet = statement.executeQuery();
            Delivery delivery = null;
            if(resultSet.next()) {
                delivery = new DeliveryResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(delivery);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Delivery> findDeliveries() {
        List<Delivery> deliveries = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("delivery"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                deliveries.add(new DeliveryResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            sqlException.getMessage();
        }
        return deliveries;
    }
}
