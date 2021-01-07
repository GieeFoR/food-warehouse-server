package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productOrder.ProductOrder;
import foodwarehouse.core.data.productOrder.ProductOrderRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcProductOrderRepository implements ProductOrderRepository {
    @Override
    public Optional<ProductOrder> createProductOrder(Order order, ProductBatch productBatch, int quantity) {
        return Optional.empty();
    }
}
