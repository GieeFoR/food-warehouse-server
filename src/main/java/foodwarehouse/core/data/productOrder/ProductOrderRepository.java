package foodwarehouse.core.data.productOrder;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductOrderRepository {

    Optional<ProductOrder> createProductOrder(Order order, ProductBatch productBatch, int quantity);

    Optional<ProductOrder> updateProductOrder(Order order, ProductBatch productBatch, int quantity);

    boolean deleteProductOrder(int orderId, int batchId);

    Optional<ProductOrder> findProductOrderById(int orderId, int batchId);

    List<ProductOrder> findProductOrderAll();

    List<ProductOrder> findProductOrderByOrderId(int orderId);
}
