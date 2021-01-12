package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.productBatch.ProductBatch;

import java.util.Date;

public record OrderProductResponse(
        @JsonProperty(value = "product_id", required = true)        int productId,
        @JsonProperty(value = "name", required = true)              String name,
        @JsonProperty(value = "sell_price", required = true)        float sellPrice,
        @JsonProperty(value = "due_to", required = true)            Date eatByDate,
        @JsonProperty(value = "image", required = true)             String image,
        @JsonProperty(value = "producer_name", required = true)     String makerName,
        @JsonProperty(value = "quantity", required = true)          int quantity) {

    public static OrderProductResponse fromProductBatch(ProductBatch productBatch, int quantity) {
        return new OrderProductResponse(
                productBatch.product().productId(),
                productBatch.product().name(),
                productBatch.product().sellPrice() * (100 - productBatch.discount()) / 100,
                productBatch.eatByDate(),
                productBatch.product().image(),
                productBatch.product().maker().firmName(),
                quantity);
    }
}
