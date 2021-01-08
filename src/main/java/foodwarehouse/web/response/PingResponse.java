package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PingResponse(
        @JsonProperty(value = "result", required = true) boolean result) {
}
