package foodwarehouse.core.service;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productOrder.ProductOrder;
import foodwarehouse.core.data.productOrder.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    @Autowired
    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    public Optional<ProductOrder> createProductOrder(Order order, ProductBatch productBatch, int quantity) {
        return productOrderRepository.createProductOrder(order, productBatch, quantity);
    }

    public Optional<ProductOrder> updateProductOrder(Order order, ProductBatch productBatch, int quantity) {
        return productOrderRepository.updateProductOrder(order, productBatch, quantity);
    }

    public boolean deleteProductOrder(int orderId, int batchId) {
        return productOrderRepository.deleteProductOrder(orderId, batchId);
    }

    public Optional<ProductOrder> findProductOrderById(int orderId, int batchId) {
        return productOrderRepository.findProductOrderById(orderId, batchId);
    }

    public List<ProductOrder> findProductOrderAll() {
        return productOrderRepository.findProductOrderAll();
    }

    public List<ProductOrder> findProductOrderByOrderId(int orderId) {
        return productOrderRepository.findProductOrderByOrderId(orderId);
    }
}
