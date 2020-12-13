package foodwarehouse.core.delivery;

import foodwarehouse.core.user.employee.Employee;
import universitymanagement.core.common.Address;

import java.util.Date;

public record Delivery(
        int deliveryId,
        Address address,
        Employee supplier,
        Date removalFromStorage,
        Date deliveryDate) {
}
