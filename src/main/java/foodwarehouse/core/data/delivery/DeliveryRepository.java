package foodwarehouse.core.data.delivery;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {

    Optional<Delivery> createDelivery(Address address, Employee supplier);

    Optional<Delivery> updateDelivery(int deliveryId, Address address, Employee supplier);

    Optional<Delivery> updateRemoveDate(int deliveryId, Address address, Employee supplier, Date date);

    Optional<Delivery> updateCompleteDate(int deliveryId, Address address, Employee supplier, Date date);

    boolean deleteDelivery (int deliveryId);

    Optional<Delivery> findDeliveryById(int deliveryId);

    List<Delivery> findDeliveries();
}
