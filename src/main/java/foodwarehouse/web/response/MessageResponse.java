package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record MessageResponse (
        @JsonProperty("message_id")     int messageId,
        @JsonProperty("sender")         EmployeeResponse senderResponse,
        @JsonProperty("receiver")       EmployeeResponse receiverReponse,
        @JsonProperty("content")        String content,
        @JsonProperty("state")          String state,
        @JsonProperty("send_date")      Date sendDate,
        @JsonProperty("read_date")      Date readDate) {
}
