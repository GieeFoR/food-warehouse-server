package foodwarehouse.core.data.product;

import foodwarehouse.core.data.maker.Maker;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> createProduct(Maker maker, String name, String category, boolean needColdStorage, float buyPrice, float sellPrice) throws SQLException;

    Optional<Product> updateProduct(int productId, String name, String category, boolean needColdStorage, float buyPrice, float sellPrice) throws SQLException;

    boolean deleteProduct(int productId) throws SQLException;

    Optional<Product> findProductById(int productId) throws SQLException;

    List<Product> findProducts() throws SQLException;
}
