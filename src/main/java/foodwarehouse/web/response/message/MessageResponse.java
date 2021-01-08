package foodwarehouse.web.response.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.web.response.employee.EmployeeResponse;

import java.util.Date;

public record MessageResponse (
        @JsonProperty(value = "message_id", required = true)     int messageId,
        @JsonProperty(value = "sender", required = true)EmployeeResponse senderResponse,
        @JsonProperty(value = "receiver", required = true)       EmployeeResponse receiverResponse,
        @JsonProperty(value = "content", required = true)        String content,
        @JsonProperty(value = "state", required = true)          String state,
        @JsonProperty(value = "send_date", required = true)      Date sendDate,
        @JsonProperty(value = "read_date")      Date readDate) {

    public static MessageResponse fromMessage(Message message) {
        return new MessageResponse(
                message.messageId(),
                EmployeeResponse.fromEmployee(message.sender()),
                EmployeeResponse.fromEmployee(message.receiver()),
                message.content(),
                message.state(),
                message.sendDate(),
                message.readDate());
    }
}
