package foodwarehouse.core.data.delivery;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;

import java.util.Date;

public record Delivery(
        int deliveryId,
        Address address,
        Employee supplier,
        Date removalFromStorage,
        Date deliveryDate) {
}
