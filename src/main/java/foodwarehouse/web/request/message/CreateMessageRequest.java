package foodwarehouse.web.request.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record CreateMessageRequest(
        @JsonProperty(value = "receiver", required = true)      int receiver,
        @JsonProperty(value = "content", required = true)       String content) {
}
