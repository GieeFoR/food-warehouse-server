package foodwarehouse.core.data.productBatch;

import foodwarehouse.core.data.product.Product;

import java.util.Date;

public record ProductBatch(
        int batchId,
        Product product,
        int batchNumber,
        Date eatByDate,
        int discount,
        int packagesQuantity) {
}
