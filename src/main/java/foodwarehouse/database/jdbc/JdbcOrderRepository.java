package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.order.OrderRepository;
import foodwarehouse.core.data.order.OrderState;
import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.database.rowmappers.DeliveryResultSetMapper;
import foodwarehouse.database.rowmappers.OrderResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.CarTable;
import foodwarehouse.database.tables.DeliveryTable;
import foodwarehouse.database.tables.OrderTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderRepository implements OrderRepository {

//    private final Connection connection;
//
//    @Autowired
//    JdbcOrderRepository(DataSource dataSource) {
//        try {
//            this.connection = dataSource.getConnection();
//        }
//        catch(SQLException sqlException) {
//            throw new RestException("Cannot connect to database!");
//        }
//    }

    @Override
    public Optional<Order> createOrder(Payment payment, Customer customer, Delivery delivery, String comment) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("order"), Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, payment.paymentId());
                statement.setInt(2, customer.customerId());
                statement.setInt(3, delivery.deliveryId());
                statement.setString(4, comment);

                statement.executeUpdate();
                int orderId = statement.getGeneratedKeys().getInt(1);
                statement.close();

                return Optional.of(new Order(orderId, payment, customer, delivery, comment, OrderState.PENDING, new Date()));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void updateOrderState(
            int orderId,
            Payment payment,
            Customer customer,
            Delivery delivery,
            String comment,
            OrderState orderState) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("order_state"));
                statement.setString(1, orderState.value());
                statement.setInt(2, orderId);

                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateOrderPayment(
            int orderId,
            Payment payment,
            Customer customer,
            Delivery delivery,
            String comment,
            OrderState orderState) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("order_payment"));
                statement.setInt(1, payment.paymentId());
                statement.setInt(2, orderId);

                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean deleteOrder(int orderId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("order"));
                statement.setInt(1, orderId);

                statement.executeUpdate();
                statement.close();

                return true;
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Order> findOrderById(int orderId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("order_byId"));
                statement.setInt(1, orderId);

                ResultSet resultSet = statement.executeQuery();
                Order order = null;
                if(resultSet.next()) {
                    order = new OrderResultSetMapper().resultSetMap(resultSet, "");
                }
                statement.close();

                return Optional.ofNullable(order);
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findOrders() {
        List<Order> orders = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("order"));

                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    orders.add(new OrderResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            orders = null;
        }
        return orders;
    }

    @Override
    public List<Order> findCustomerOrders(int customerId) {
        List<Order> orders = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("order_byCustomerId"));
                statement.setInt(1, customerId);

                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    orders.add(new OrderResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            orders = null;
        }
        return orders;
    }

    @Override
    public int amountOfOrdersBetween(String startDate, String endDate) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("order_amountBetween"));
                statement.setString(1, startDate);
                statement.setString(2, endDate);

                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    return resultSet.getInt(1);
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public List<Order> findOrdersBetweenDates(String startDate, String endDate) {
        List<Order> orders = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("order_between"));
                statement.setString(1, startDate);
                statement.setString(2, endDate);

                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    orders.add(new OrderResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            orders = null;
        }
        return orders;
    }

    @Override
    public List<Order> findSupplierActiveOrders(int employeeId) {
        List<Order> orders = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("order_supplierActive"));
                statement.setInt(1, employeeId);

                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    orders.add(new OrderResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            orders = null;
        }
        return orders;
    }
}
