package foodwarehouse.core.data.message;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.user.User;
import foodwarehouse.web.response.EmployeeResponse;
import foodwarehouse.web.response.MessageResponse;
import foodwarehouse.web.response.UserResponse;

import java.util.Date;

public record Message(
        int messageId,
        Employee sender,
        Employee receiver,
        String content,
        String state,
        Date sendDate,
        Date readDate) {
}
