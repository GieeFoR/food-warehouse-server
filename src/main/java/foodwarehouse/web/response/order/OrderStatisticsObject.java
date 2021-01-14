package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderStatisticsObject(
        @JsonProperty(value = "date", required = true)      String date,
        @JsonProperty(value = "orders", required = true)    int ordersAmount) {
}
