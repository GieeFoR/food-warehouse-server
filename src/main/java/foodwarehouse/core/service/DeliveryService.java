package foodwarehouse.core.service;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.delivery.DeliveryRepository;
import foodwarehouse.core.data.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService implements DeliveryRepository {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Optional<Delivery> createDelivery(Address address, Employee supplier) throws SQLException {
        return deliveryRepository.createDelivery(address, supplier);
    }

    @Override
    public Optional<Delivery> updateRemoveDate(int deliveryId, Date date) throws SQLException {
        return deliveryRepository.updateRemoveDate(deliveryId, date);
    }

    @Override
    public Optional<Delivery> updateCompleteDate(int deliveryId, Date date) throws SQLException {
        return deliveryRepository.updateCompleteDate(deliveryId, date);
    }

    @Override
    public boolean deleteDelivery(int deliveryId) throws SQLException {
        return deliveryRepository.deleteDelivery(deliveryId);
    }

    @Override
    public Optional<Delivery> findDeliveryById(int deliveryId) throws SQLException {
        return deliveryRepository.findDeliveryById(deliveryId);
    }

    @Override
    public List<Delivery> findDeliveries() throws SQLException {
        return deliveryRepository.findDeliveries();
    }
}
