package foodwarehouse.core.message;

import foodwarehouse.core.user.employee.Employee;

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
