package foodwarehouse.web.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.product.Product;

public record ProductDataResponse(
        @JsonProperty(value = "product_id", required = true)    int productId,
        @JsonProperty(value = "name", required = true)          String name,
        @JsonProperty(value = "category", required = true)      String category,
        @JsonProperty(value = "needs_cold", required = true)    boolean needColdStorage,
        @JsonProperty(value = "buy_price",required = true)      float buyPrice,
        @JsonProperty(value = "sell_price", required = true)    float sellPrice) {

    public static ProductDataResponse fromProduct(Product product) {
        return new ProductDataResponse(
                product.productId(),
                product.name(),
                product.category(),
                product.needColdStorage(),
                product.buyPrice(),
                product.sellPrice());
    }
}
