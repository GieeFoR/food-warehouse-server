package foodwarehouse.core.productBatch;

import foodwarehouse.core.product.Product;

import java.util.Date;

public record ProductBatch(
        int batchId,
        Product product,
        int batchNumber,
        Date eatByDate,
        int discount,
        int packagesQuantity) {
}
