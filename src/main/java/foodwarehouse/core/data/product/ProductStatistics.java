package foodwarehouse.core.data.product;

public record ProductStatistics(
        Product product,
        int regularQuantity,
        int discountQuantity) {
}
