package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record CreateMessageRequest(
        @JsonProperty(value = "sender", required = true)        int sender,
        @JsonProperty(value = "receiver", required = true)      int receiver,
        @JsonProperty(value = "content", required = true)       String content,
        @JsonProperty(value = "state", required = true)         String state,
        @JsonProperty(value = "send_date", required = true)     Date sendDate,
        @JsonProperty(value = "read_date", required = true)     Date readDate) {
}
