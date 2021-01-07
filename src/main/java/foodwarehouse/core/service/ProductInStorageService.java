package foodwarehouse.core.service;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.productInStorage.ProductInStorageRepository;
import foodwarehouse.core.data.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class ProductInStorageService {

    private final ProductInStorageRepository productInStorageRepository;

    @Autowired
    public ProductInStorageService(ProductInStorageRepository productInStorageRepository) {
        this.productInStorageRepository = productInStorageRepository;
    }

    public Optional<ProductInStorage> createProductInStorage(ProductBatch productBatch, Storage storage, int quantity) {
        return productInStorageRepository.createProductInStorage(productBatch, storage, quantity);
    }
}
