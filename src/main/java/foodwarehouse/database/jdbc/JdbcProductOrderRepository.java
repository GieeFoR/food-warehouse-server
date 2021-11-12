package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productOrder.ProductOrder;
import foodwarehouse.core.data.productOrder.ProductOrderRepository;
import foodwarehouse.database.rowmappers.ProductOrderResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.ProductOrderTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductOrderRepository implements ProductOrderRepository {

//    private final Connection connection;
//
//    @Autowired
//    JdbcProductOrderRepository(DataSource dataSource) {
//        try {
//            this.connection = dataSource.getConnection();
//        }
//        catch(SQLException sqlException) {
//            throw new RestException("Cannot connect to database!");
//        }
//    }

    @Override
    public Optional<ProductOrder> createProductOrder(Order order, ProductBatch productBatch, int quantity) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("productOrder"), Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, order.orderId());
                statement.setInt(2, productBatch.batchId());
                statement.setInt(3, quantity);

                statement.executeUpdate();
                statement.close();

                return Optional.of(new ProductOrder(order, productBatch, quantity));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProductOrder> updateProductOrder(Order order, ProductBatch productBatch, int quantity) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("productOrder"));
                statement.setInt(1, quantity);
                statement.setInt(2, order.orderId());
                statement.setInt(3, productBatch.batchId());

                statement.executeUpdate();
                statement.close();

                return Optional.of(new ProductOrder(order, productBatch, quantity));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteProductOrder(int orderId, int productBatchId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("productOrder"));
                statement.setInt(1, orderId);
                statement.setInt(2, productBatchId);

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
    public Optional<ProductOrder> findProductOrderById(int orderId, int productBatchId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productOrder_byId"));
                statement.setInt(1, orderId);
                statement.setInt(2, productBatchId);

                ResultSet resultSet = statement.executeQuery();
                ProductOrder productOrder = null;
                if(resultSet.next()) {
                    productOrder = new ProductOrderResultSetMapper().resultSetMap(resultSet, "");
                }
                statement.close();

                return Optional.ofNullable(productOrder);
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<ProductOrder> findProductOrderAll() {
        List<ProductOrder> productOrders = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productOrder"));

                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    productOrders.add(new ProductOrderResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            productOrders = null;
        }
        return productOrders;
    }

    @Override
    public List<ProductOrder> findProductOrderByOrderId(int orderId) {
        List<ProductOrder> productOrders = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productOrder_byOrderId"));
                statement.setInt(1, orderId);

                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    productOrders.add(new ProductOrderResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            productOrders = null;
        }
        return productOrders;
    }
}
