package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProfitStatisticsResponse(
        @JsonProperty(value = "profits", required = true)       List<Float> profits,
        @JsonProperty(value = "dates", required = true)         List<String> dates) {
}
