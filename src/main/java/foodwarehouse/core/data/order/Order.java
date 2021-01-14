package foodwarehouse.core.data.order;

import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.customer.Customer;

import java.util.Date;

public record Order(
        int orderId,
        Payment payment,
        Customer customer,
        Delivery delivery,
        String comment,
        OrderState state,
        Date orderDate) {
}
