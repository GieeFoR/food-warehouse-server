package foodwarehouse.core.service;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.productInStorage.ProductInStorageRepository;
import foodwarehouse.core.data.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
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

    public Optional<ProductInStorage> updateProductInStorage(ProductBatch productBatch, Storage storage, int quantity) {
        return productInStorageRepository.updateProductInStorage(productBatch, storage, quantity);
    }

    public boolean deleteProductInStorage(ProductBatch productBatch, Storage storage) {
        return productInStorageRepository.deleteProductInStorage(productBatch, storage);
    }

    public Optional<ProductInStorage> findProductInStorageById(ProductBatch productBatch, Storage storage) {
        return productInStorageRepository.findProductInStorageById(productBatch, storage);
    }

    public List<ProductInStorage> findProductInStorageAll() {
        return productInStorageRepository.findProductInStorageAll();
    }

    public List<ProductInStorage> findExpiredProductsInStorages() {
        return productInStorageRepository.findExpiredProductInStorage();
    }

    public List<ProductInStorage> findProductInStorageAllByProductId(int productId) {
        return productInStorageRepository.findProductInStorageAllByProductId(productId);
    }

    public List<ProductInStorage> findProductInStorageAllByBatchId(int batchId) {
        return productInStorageRepository.findProductInStorageAllByBatchId(batchId);
    }

    public float findProductPrice(int batchId) {
        return productInStorageRepository.findProductPrice(batchId);
    }
}
