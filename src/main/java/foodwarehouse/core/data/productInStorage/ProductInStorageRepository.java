package foodwarehouse.core.data.productInStorage;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.storage.Storage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductInStorageRepository {

    Optional<ProductInStorage> createProductInStorage(ProductBatch productBatch, Storage storage, int quantity);

    Optional<ProductInStorage> updateProductInStorage(ProductBatch productBatch, Storage storage, int quantity);

    boolean deleteProductInStorage(ProductBatch productBatch, Storage storage);

    List<ProductInStorage> findProductInStorageAll();

    Optional<ProductInStorage> findProductInStorageById(ProductBatch productBatch, Storage storage);
}
