package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OrderStatisticsResponse(
        @JsonProperty(value = "amounts", required = true)       List<Integer> amountsList,
        @JsonProperty(value = "dates", required = true)         List<String> datesList,
        @JsonProperty(value = "objects", required = true)       List<OrderStatisticsObject> objects) {
}
