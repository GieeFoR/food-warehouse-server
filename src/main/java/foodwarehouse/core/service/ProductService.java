package foodwarehouse.core.service;

import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> createProduct(
            Maker maker,
            String name,
            String shortDesc,
            String longDesc,
            String category,
            boolean needColdStorage,
            float buyPrice,
            float sellPrice,
            String image) {
        return productRepository.createProduct(maker, name, shortDesc, longDesc, category, needColdStorage, buyPrice, sellPrice, image);
    }

    public Optional<Product> updateProduct(
            int productId,
            Maker maker,
            String name,
            String shortDesc,
            String longDesc,
            String category,
            boolean needColdStorage,
            float buyPrice,
            float sellPrice,
            String image) {
        return productRepository.updateProduct(productId,  maker, name, shortDesc, longDesc, category, needColdStorage, buyPrice, sellPrice, image);
    }

    public boolean deleteProduct(int productId) {
        return productRepository.deleteProduct(productId);
    }

    public Optional<Product> findProductById(int productId) {
        return productRepository.findProductById(productId);
    }

    public List<Product> findProducts() {
        return productRepository.findProducts();
    }
}
