package foodwarehouse.core.data.productBatch;

import foodwarehouse.core.data.product.Product;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductBatchRepository {

    Optional<ProductBatch> createProductBatch(Product product, int batchNo, Date eatByDate, int quantity);

    Optional<ProductBatch> updateProductBatch(int productBatchId, Product product, int batchNo, Date eatByDate, int discount, int quantity);

    boolean deleteProductBatch(int productBatchId);

    Optional<ProductBatch> findProductBatchById(int productBatchId);

    List<ProductBatch> findProductBatches();

    List<ProductBatch> findProductBatchesByProductId(int productId);

    List<ProductBatch> findProductBatchesWithDiscountAndProductId(int productId);

    int countProductBatchAmount(int productId);
}
