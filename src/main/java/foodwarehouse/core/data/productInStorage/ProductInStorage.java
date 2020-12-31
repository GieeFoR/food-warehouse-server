package foodwarehouse.core.data.productInStorage;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.storage.Storage;

public record ProductInStorage(
        ProductBatch batch,
        Storage storage,
        int quantity) {
}
