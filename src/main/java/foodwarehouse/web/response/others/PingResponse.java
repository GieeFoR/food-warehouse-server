package foodwarehouse.web.response.others;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PingResponse(
        @JsonProperty(value = "result", required = true) boolean result) {
}
