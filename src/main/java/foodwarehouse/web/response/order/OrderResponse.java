package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderResponse(
        @JsonProperty(value = "dziaa", required = true) boolean dziaa
) {
}
