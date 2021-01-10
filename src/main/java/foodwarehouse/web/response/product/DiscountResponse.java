package foodwarehouse.web.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record DiscountResponse(
        @JsonProperty(value = "eat_by_date", required = true)   Date eatByDate,
        @JsonProperty(value = "quantity", required = true)      int quantity) {
}
