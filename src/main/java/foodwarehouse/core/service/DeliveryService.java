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
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Optional<Delivery> createDelivery(Address address, Employee supplier) {
        return deliveryRepository.createDelivery(address, supplier);
    }

    public Optional<Delivery> updateRemoveDate(int deliveryId, Date date) {
        return deliveryRepository.updateRemoveDate(deliveryId, date);
    }

    public Optional<Delivery> updateCompleteDate(int deliveryId, Date date) {
        return deliveryRepository.updateCompleteDate(deliveryId, date);
    }

    public boolean deleteDelivery(int deliveryId) {
        return deliveryRepository.deleteDelivery(deliveryId);
    }

    public Optional<Delivery> findDeliveryById(int deliveryId) {
        return deliveryRepository.findDeliveryById(deliveryId);
    }

    public List<Delivery> findDeliveries() {
        return deliveryRepository.findDeliveries();
    }
}
