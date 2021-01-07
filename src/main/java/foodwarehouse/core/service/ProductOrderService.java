package foodwarehouse.core.service;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productOrder.ProductOrder;
import foodwarehouse.core.data.productOrder.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class ProductOrderService implements ProductOrderRepository {

    private final ProductOrderRepository productOrderRepository;

    @Autowired
    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    public Optional<ProductOrder> createProductOrder(Order order, ProductBatch productBatch, int quantity) throws SQLException {
        return productOrderRepository.createProductOrder(order, productBatch, quantity);
    }
}
