package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PingResponse(
        @JsonProperty("result") boolean result) {
}
