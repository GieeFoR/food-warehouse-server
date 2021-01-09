package foodwarehouse.core.data.product;

import foodwarehouse.core.data.maker.Maker;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> createProduct(
            Maker maker,
            String name,
            String category,
            String shortDesc,
            String longDesc,
            boolean needColdStorage,
            float buyPrice,
            float sellPrice,
            String image);

    Optional<Product> updateProduct(
            int productId,
            Maker maker,
            String name,
            String shortDesc,
            String longDesc,
            String category,
            boolean needColdStorage,
            float buyPrice,
            float sellPrice,
            String image);

    boolean deleteProduct(int productId);

    Optional<Product> findProductById(int productId);

    List<Product> findProducts();
}
