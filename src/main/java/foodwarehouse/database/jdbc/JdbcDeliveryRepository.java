package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.delivery.DeliveryRepository;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.database.tables.CarTable;
import foodwarehouse.database.tables.DeliveryTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
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
            CallableStatement callableStatement = connection.prepareCall(DeliveryTable.Procedures.INSERT);
            callableStatement.setInt(1, address.addressId());
            callableStatement.setInt(2, supplier.employeeId());

            callableStatement.executeQuery();
            int deliveryId = callableStatement.getInt(8);
            return Optional.of(new Delivery(deliveryId, address, supplier, null, null));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Delivery> updateRemoveDate(int deliveryId, Address address, Employee supplier, Date date) {
        try {
            CallableStatement callableStatement = connection.prepareCall(DeliveryTable.Procedures.UPDATE_REMOVE);
            callableStatement.setInt(1, deliveryId);
            callableStatement.setDate(2, new java.sql.Date(date.getTime()));

            callableStatement.executeQuery();

            return Optional.of(new Delivery(deliveryId, address, supplier, date, null));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Delivery> updateCompleteDate(int deliveryId, Address address, Employee supplier, Date date) {
        try {
            CallableStatement callableStatement = connection.prepareCall(DeliveryTable.Procedures.UPDATE_COMPLETE);
            callableStatement.setInt(1, deliveryId);
            callableStatement.setDate(2, new java.sql.Date(date.getTime()));

            callableStatement.executeQuery();

            return Optional.of(new Delivery(deliveryId, address, supplier, date, null));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteDelivery(int deliveryId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(DeliveryTable.Procedures.DELETE);
            callableStatement.setInt(1, deliveryId);

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<Delivery> findDeliveryById(int deliveryId) {
        return Optional.empty();
    }

    @Override
    public List<Delivery> findDeliveries() {
        return null;
    }
}
