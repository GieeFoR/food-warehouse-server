package foodwarehouse.core.service;

import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productBatch.ProductBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBatchService {

    private final ProductBatchRepository productBatchRepository;

    @Autowired
    public ProductBatchService(ProductBatchRepository productBatchRepository) {
        this.productBatchRepository = productBatchRepository;
    }

    public Optional<ProductBatch> createProductBatch(Product product, int batchNo, Date eatByDate, int quantity) {
        return productBatchRepository.createProductBatch(product, batchNo, eatByDate, quantity);
    }

    public Optional<ProductBatch> updateProductBatch(int productBatchId, int batchNo, Date eatByDate, int discount, int quantity) {
        return productBatchRepository.updateProductBatch(productBatchId, batchNo, eatByDate, discount, quantity);
    }

    public boolean deleteProductBatch(int productBatchId) {
        return productBatchRepository.deleteProductBatch(productBatchId);
    }

    public Optional<ProductBatch> findProductBatchById(int productBatchId) {
        return productBatchRepository.findProductBatchById(productBatchId);
    }

    public List<ProductBatch> findProductBatches() {
        return productBatchRepository.findProductBatches();
    }
}
