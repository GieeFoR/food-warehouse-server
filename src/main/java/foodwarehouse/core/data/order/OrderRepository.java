package foodwarehouse.core.data.order;

import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.payment.Payment;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> createOrder(Payment payment, Customer customer, Delivery delivery, String comment) throws SQLException;

    Optional<Order> updateOrderState(int orderId, OrderState orderState) throws SQLException;

    boolean deleteOrder(int orderId) throws SQLException;

    Optional<Order> findOrderById(int orderId) throws SQLException;

    List<Order> findOrders() throws SQLException;
}
