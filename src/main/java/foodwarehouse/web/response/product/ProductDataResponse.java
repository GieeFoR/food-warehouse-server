package foodwarehouse.web.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.product.Product;

import java.sql.Blob;

public record ProductDataResponse(
        @JsonProperty(value = "product_id", required = true)    int productId,
        @JsonProperty(value = "name", required = true)          String name,
        @JsonProperty(value = "short_description")              String shortDesc,
        @JsonProperty(value = "description")                    String longDesc,
        @JsonProperty(value = "category", required = true)      String category,
        @JsonProperty(value = "needs_cold", required = true)    boolean needColdStorage,
        @JsonProperty(value = "image", required = true)         String image,
        @JsonProperty(value = "sell_price", required = true)    float sellPrice,
        @JsonProperty(value = "buy_price", required = true)     float buyPrice) {

    public static ProductDataResponse fromProduct(Product product) {
        return new ProductDataResponse(
                product.productId(),
                product.name(),
                product.shortDesc(),
                product.longDesc(),
                product.category(),
                product.needColdStorage(),
                product.image(),
                product.sellPrice(),
                product.buyPrice());
    }
}
