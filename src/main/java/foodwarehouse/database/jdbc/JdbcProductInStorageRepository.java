package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.productInStorage.ProductInStorageRepository;
import foodwarehouse.core.data.storage.Storage;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcProductInStorageRepository implements ProductInStorageRepository {
    @Override
    public Optional<ProductInStorage> createProductInStorage(ProductBatch productBatch, Storage storage, int quantity) {
        return Optional.empty();
    }
}
