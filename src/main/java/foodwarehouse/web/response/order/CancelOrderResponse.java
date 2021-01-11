package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelOrderResponse(
        @JsonProperty(value = "canceled", required = true) boolean isCanceled) {
}
