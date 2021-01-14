package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProductStatisticsResponse(
        @JsonProperty(value = "products", required = true)               List<String> productLabel,
        @JsonProperty(value = "quantities_regular", required = true)       List<Integer> regularQuantities,
        @JsonProperty(value = "quantities_discount", required = true)      List<Integer> discountQuantities,
        @JsonProperty(value = "quantities_all", required = true)           List<Integer> allProductsQuantities) {
}
