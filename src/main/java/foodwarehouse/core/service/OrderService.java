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
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> createOrder(Payment payment, Customer customer, Delivery delivery, String comment) {
        return orderRepository.createOrder(payment, customer, delivery, comment);
    }

    public Optional<Order> updateOrderState(int orderId, OrderState orderState) {
        return orderRepository.updateOrderState(orderId, orderState);
    }

    public boolean deleteOrder(int orderId) {
        return orderRepository.deleteOrder(orderId);
    }

    public Optional<Order> findOrderById(int orderId) {
        return orderRepository.findOrderById(orderId);
    }

    public List<Order> findOrders() {
        return orderRepository.findOrders();
    }
}
