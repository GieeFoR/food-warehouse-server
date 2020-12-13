package foodwarehouse.core.order;

import foodwarehouse.core.delivery.Delivery;
import foodwarehouse.core.payment.Payment;
import foodwarehouse.core.user.customer.Customer;

public record Order(
        int orderId,
        Payment payment,
        Customer customer,
        Delivery delivery,
        String comment,
        String state) {
}
