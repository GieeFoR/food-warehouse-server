package foodwarehouse.web.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.productBatch.ProductBatch;

import java.text.SimpleDateFormat;
import java.util.Date;

public record DiscountResponse(
        @JsonProperty(value = "discount_id", required = true)   int discountId,
        @JsonProperty(value = "eat_by_date", required = true)   String eatByDate,
        @JsonProperty(value = "quantity", required = true)      int quantity,
        @JsonProperty(value = "sell_price", required = true)    float sellPrice) {

    public static DiscountResponse fromProductBatch(ProductBatch productBatch, int quantity, float regularPrice) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new DiscountResponse(
                productBatch.batchId(),
                sdf.format(productBatch.eatByDate()),
                quantity,
                regularPrice * (100 - productBatch.discount()) / 100);
    }
}
