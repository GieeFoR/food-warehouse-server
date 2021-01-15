package foodwarehouse.web.response.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.web.response.employee.EmployeeMessageResponse;
import foodwarehouse.web.response.employee.EmployeeResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public record MessageResponse (
        @JsonProperty(value = "message_id", required = true)     int messageId,
        @JsonProperty(value = "sender", required = true)         EmployeeMessageResponse senderResponse,
        @JsonProperty(value = "receiver", required = true)       EmployeeMessageResponse receiverResponse,
        @JsonProperty(value = "content", required = true)        String content,
        @JsonProperty(value = "state", required = true)          String state,
        @JsonProperty(value = "send_date", required = true)      String sendDate,
        @JsonProperty(value = "read_date")                       String readDate) {

    public static MessageResponse fromMessage(Message message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new MessageResponse(
                message.messageId(),
                EmployeeMessageResponse.fromEmployee(message.sender()),
                EmployeeMessageResponse.fromEmployee(message.receiver()),
                message.content(),
                message.state(),
                sdf.format(message.sendDate()),
                message.readDate() == null ? null : sdf.format(message.readDate()));
    }
}
