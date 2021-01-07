package foodwarehouse.core.data.productInStorage;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.storage.Storage;

import java.sql.SQLException;
import java.util.Optional;

public interface ProductInStorageRepository {

    Optional<ProductInStorage> createProductInStorage(ProductBatch productBatch, Storage storage, int quantity);

}
