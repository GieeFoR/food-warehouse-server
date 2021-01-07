package foodwarehouse.core.data.delivery;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {

    Optional<Delivery> createDelivery(Address address, Employee supplier) throws SQLException;

    Optional<Delivery> updateRemoveDate(int deliveryId, Date date) throws SQLException;

    Optional<Delivery> updateCompleteDate(int deliveryId, Date date) throws SQLException;

    boolean deleteDelivery (int deliveryId) throws SQLException;

    Optional<Delivery> findDeliveryById(int deliveryId) throws SQLException;

    List<Delivery> findDeliveries() throws SQLException;
}
