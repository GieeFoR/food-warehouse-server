package foodwarehouse.web.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductInOrderData(
        @JsonProperty(value = "product_id", required = true)        int productId,
        @JsonProperty(value = "discount_id", required = true)       int discountId,
        @JsonProperty(value = "quantity", required = true)          int quantity) {
}
