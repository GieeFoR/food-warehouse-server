package foodwarehouse.core.data.order;

import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.payment.Payment;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> createOrder(Payment payment, Customer customer, Delivery delivery, String comment);

    void updateOrderState(int orderId, Payment payment, Customer customer, Delivery delivery, String comment, OrderState orderState);

    void updateOrderPayment(int orderId, Payment payment, Customer customer, Delivery delivery, String comment, OrderState orderState);

    boolean deleteOrder(int orderId);

    Optional<Order> findOrderById(int orderId);

    List<Order> findOrders();

    List<Order> findCustomerOrders(int customerId);

    int amountOfOrdersBetween(String startDate, String endDate);

    List<Order> findOrdersBetweenDates(String startDate, String endDate);

    List<Order> findSupplierActiveOrders(int employeeId);
}
