package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.order.OrderRepository;
import foodwarehouse.core.data.order.OrderState;
import foodwarehouse.core.data.payment.Payment;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    @Override
    public Optional<Order> createOrder(Payment payment, Customer customer, Delivery delivery, String comment) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> updateOrderState(int orderId, OrderState orderState) {
        return Optional.empty();
    }

    @Override
    public boolean deleteOrder(int orderId) {
        return false;
    }

    @Override
    public Optional<Order> findOrderById(int orderId) {
        return Optional.empty();
    }

    @Override
    public List<Order> findOrders() {
        return null;
    }
}
