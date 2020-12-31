package foodwarehouse.core.data.productOrder;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;

public record ProductOrder(
        Order order,
        ProductBatch batch,
        int quantity) {
}
