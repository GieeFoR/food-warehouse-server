package foodwarehouse.core.data.order;

import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.customer.Customer;

public record Order(
        int orderId,
        Payment payment,
        Customer customer,
        Delivery delivery,
        String comment,
        String state) {
}
