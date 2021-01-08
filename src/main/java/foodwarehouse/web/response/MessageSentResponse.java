package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MessageSentResponse (
        @JsonProperty(value = "sent", required = true)  boolean sent) {

    public static MessageSentResponse fromBoolean(Boolean sent) {
        return new MessageSentResponse(sent);
    }
}
