package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.core.data.user.User;

import java.util.Date;

public record MessageResponse (
        @JsonProperty("message_id")     int messageId,
        @JsonProperty("sender")         EmployeeResponse senderResponse,
        @JsonProperty("receiver")       EmployeeResponse receiverReponse,
        @JsonProperty("content")        String content,
        @JsonProperty("state")          String state,
        @JsonProperty("send_date")      Date sendDate,
        @JsonProperty("read_date")      Date readDate) {

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
