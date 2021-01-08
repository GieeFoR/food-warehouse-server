package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateProductRequest(
        @JsonProperty(value = "maker_id", required = true)      int makerId,
        @JsonProperty(value = "name", required = true)          String name,
        @JsonProperty(value = "category", required = true)      String category,
        @JsonProperty(value = "needs_cold", required = true)    boolean needColdStorage,
        @JsonProperty(value = "buy_price",required = true)      float buyPrice,
        @JsonProperty(value = "sell_price", required = true)    float sellPrice) {
}
