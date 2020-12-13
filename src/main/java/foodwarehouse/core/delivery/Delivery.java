package foodwarehouse.core.delivery;

import foodwarehouse.core.user.employee.Employee;
import org.apache.tomcat.jni.Address;

import java.util.Date;

public record Delivery(
        int deliveryId,
        Address address,
        Employee supplier,
        Date removalFromStorage,
        Date deliveryDate) {
}
