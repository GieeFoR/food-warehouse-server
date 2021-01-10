package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productOrder.ProductOrder;
import foodwarehouse.core.data.productOrder.ProductOrderRepository;
import foodwarehouse.database.rowmappers.ProductOrderResultSetMapper;
import foodwarehouse.database.tables.ProductOrderTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductOrderRepository implements ProductOrderRepository {

    private final Connection connection;

    @Autowired
    JdbcProductOrderRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<ProductOrder> createProductOrder(Order order, ProductBatch productBatch, int quantity) {

        try {
            CallableStatement callableStatement = connection.prepareCall(ProductOrderTable.Procedures.INSERT);
            callableStatement.setInt(1, order.orderId());
            callableStatement.setInt(2, productBatch.batchId());
            callableStatement.setInt(3, quantity);

            callableStatement.executeQuery();
            return Optional.of(new ProductOrder(order, productBatch, quantity));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProductOrder> updateProductOrder(Order order, ProductBatch productBatch, int quantity) {

        try {
            CallableStatement callableStatement = connection.prepareCall(ProductOrderTable.Procedures.UPDATE);
            callableStatement.setInt(1, order.orderId());
            callableStatement.setInt(2, productBatch.batchId());
            callableStatement.setInt(3, quantity);

            callableStatement.executeQuery();
            return Optional.of(new ProductOrder(order, productBatch, quantity));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteProductOrder(Order order, ProductBatch productBatch) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductOrderTable.Procedures.DELETE);
            callableStatement.setInt(1, order.orderId());
            callableStatement.setInt(2, productBatch.batchId());

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<ProductOrder> findProductOrderById(Order order, ProductBatch productBatch) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductOrderTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, order.orderId());
            callableStatement.setInt(2, productBatch.batchId());

            ResultSet resultSet = callableStatement.executeQuery();
            ProductOrder productOrder = null;
            if(resultSet.next()) {
                productOrder = new ProductOrderResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(productOrder);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<ProductOrder> findProductOrderAll() {
        List<ProductOrder> productOrders = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductOrderTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                productOrders.add(new ProductOrderResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return productOrders;
    }
}
