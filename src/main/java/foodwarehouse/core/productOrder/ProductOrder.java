package foodwarehouse.core.productOrder;

import foodwarehouse.core.order.Order;
import foodwarehouse.core.productBatch.ProductBatch;

public record ProductOrder(
        Order order,
        ProductBatch batch,
        int quantity) {
}
