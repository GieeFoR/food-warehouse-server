package foodwarehouse.core.productInStorage;

import foodwarehouse.core.productBatch.ProductBatch;
import foodwarehouse.core.storage.Storage;

public record ProductInStorage(
        ProductBatch batch,
        Storage storage,
        int quantity) {
}
