package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productBatch.ProductBatchRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductBatchRepository implements ProductBatchRepository {
    @Override
    public Optional<ProductBatch> createProductBatch(Product product, int batchNo, Date eatByDate, int quantity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<ProductBatch> updateProductBatch(int productBatchId, int batchNo, Date eatByDate, int discount, int quantity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public boolean deleteProductBatch(int productBatchId) throws SQLException {
        return false;
    }

    @Override
    public Optional<ProductBatch> findProductBatchById(int productBatchId) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<ProductBatch> findProductBatches() throws SQLException {
        return null;
    }
}
