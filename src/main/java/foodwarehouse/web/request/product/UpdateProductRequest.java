package foodwarehouse.web.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Blob;

public record UpdateProductRequest(
        @JsonProperty(value = "product_id", required = true)    int productId,
        @JsonProperty(value = "maker_id", required = true)      int makerId,
        @JsonProperty(value = "name", required = true)          String name,
        @JsonProperty(value = "short_description")              String shortDesc,
        @JsonProperty(value = "description")                    String longDesc,
        @JsonProperty(value = "category", required = true)      String category,
        @JsonProperty(value = "needs_cold", required = true)    boolean needColdStorage,
        @JsonProperty(value = "buy_price",required = true)      float buyPrice,
        @JsonProperty(value = "sell_price", required = true)    float sellPrice,
        @JsonProperty(value = "image", required = true)         Blob image) {
}
