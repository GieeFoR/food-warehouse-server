package foodwarehouse.core.service;

import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductRepository {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> createProduct(Maker maker, String name, String category, boolean needColdStorage, float buyPrice, float sellPrice) throws SQLException {
        return productRepository.createProduct(maker, name, category, needColdStorage, buyPrice, sellPrice);
    }

    @Override
    public Optional<Product> updateProduct(int productId, String name, String category, boolean needColdStorage, float buyPrice, float sellPrice) throws SQLException {
        return productRepository.updateProduct(productId, name, category, needColdStorage, buyPrice, sellPrice);
    }

    @Override
    public boolean deleteProduct(int productId) throws SQLException {
        return productRepository.deleteProduct(productId);
    }

    @Override
    public Optional<Product> findProductById(int productId) throws SQLException {
        return productRepository.findProductById(productId);
    }

    @Override
    public List<Product> findProducts() throws SQLException {
        return productRepository.findProducts();
    }
}
