package foodwarehouse.core.data.productOrder;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;

import java.sql.SQLException;
import java.util.Optional;

public interface ProductOrderRepository {

    Optional<ProductOrder> createProductOrder(Order order, ProductBatch productBatch, int quantity) throws SQLException;
}
