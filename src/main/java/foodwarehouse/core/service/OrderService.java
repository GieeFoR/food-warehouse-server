package foodwarehouse.core.service;

import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.order.OrderRepository;
import foodwarehouse.core.data.order.OrderState;
import foodwarehouse.core.data.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderRepository {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> createOrder(Payment payment, Customer customer, Delivery delivery, String comment) throws SQLException {
        return orderRepository.createOrder(payment, customer, delivery, comment);
    }

    @Override
    public Optional<Order> updateOrderState(int orderId, OrderState orderState) throws SQLException {
        return orderRepository.updateOrderState(orderId, orderState);
    }

    @Override
    public boolean deleteOrder(int orderId) throws SQLException {
        return orderRepository.deleteOrder(orderId);
    }

    @Override
    public Optional<Order> findOrderById(int orderId) throws SQLException {
        return orderRepository.findOrderById(orderId);
    }

    @Override
    public List<Order> findOrders() throws SQLException {
        return orderRepository.findOrders();
    }
}
