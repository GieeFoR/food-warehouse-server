package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.delivery.DeliveryRepository;
import foodwarehouse.core.data.employee.Employee;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcDeliveryRepository implements DeliveryRepository {
    @Override
    public Optional<Delivery> createDelivery(Address address, Employee supplier) {
        return Optional.empty();
    }

    @Override
    public Optional<Delivery> updateRemoveDate(int deliveryId, Date date) {
        return Optional.empty();
    }

    @Override
    public Optional<Delivery> updateCompleteDate(int deliveryId, Date date) {
        return Optional.empty();
    }

    @Override
    public boolean deleteDelivery(int deliveryId) {
        return false;
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
