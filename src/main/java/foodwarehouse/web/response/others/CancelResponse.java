package foodwarehouse.web.response.others;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelResponse(
        @JsonProperty(value = "canceled", required = true) boolean isCanceled) {
}
