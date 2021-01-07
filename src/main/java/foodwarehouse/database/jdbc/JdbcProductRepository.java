package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductRepository implements ProductRepository {
    @Override
    public Optional<Product> createProduct(Maker maker, String name, String category, boolean needColdStorage, float buyPrice, float sellPrice) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> updateProduct(int productId, String name, String category, boolean needColdStorage, float buyPrice, float sellPrice) {
        return Optional.empty();
    }

    @Override
    public boolean deleteProduct(int productId) {
        return false;
    }

    @Override
    public Optional<Product> findProductById(int productId) {
        return Optional.empty();
    }

    @Override
    public List<Product> findProducts() {
        return null;
    }
}
