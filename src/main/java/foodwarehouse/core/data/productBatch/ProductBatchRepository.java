package foodwarehouse.core.data.productBatch;

import foodwarehouse.core.data.product.Product;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductBatchRepository {

    Optional<ProductBatch> createProductBatch(Product product, int batchNo, Date eatByDate, int discount, int quantity) throws SQLException;

    Optional<ProductBatch> updateProductBatch(int productBatchId, int batchNo, Date eatByDate, int discount, int quantity) throws SQLException;

    boolean deleteProductBatch(int productBatchId) throws SQLException;

    Optional<ProductBatch> findProductBatchById(int productBatchId) throws SQLException;

    List<ProductBatch> findProductBatches() throws SQLException;
}
