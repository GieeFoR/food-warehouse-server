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
public class ProductBatchService implements ProductBatchRepository {

    private final ProductBatchRepository productBatchRepository;

    @Autowired
    public ProductBatchService(ProductBatchRepository productBatchRepository) {
        this.productBatchRepository = productBatchRepository;
    }

    @Override
    public Optional<ProductBatch> createProductBatch(Product product, int batchNo, Date eatByDate, int quantity) throws SQLException {
        return productBatchRepository.createProductBatch(product, batchNo, eatByDate, quantity);
    }

    @Override
    public Optional<ProductBatch> updateProductBatch(int productBatchId, int batchNo, Date eatByDate, int discount, int quantity) throws SQLException {
        return productBatchRepository.updateProductBatch(productBatchId, batchNo, eatByDate, discount, quantity);
    }

    @Override
    public boolean deleteProductBatch(int productBatchId) throws SQLException {
        return productBatchRepository.deleteProductBatch(productBatchId);
    }

    @Override
    public Optional<ProductBatch> findProductBatchById(int productBatchId) throws SQLException {
        return productBatchRepository.findProductBatchById(productBatchId);
    }

    @Override
    public List<ProductBatch> findProductBatches() throws SQLException {
        return productBatchRepository.findProductBatches();
    }
}
