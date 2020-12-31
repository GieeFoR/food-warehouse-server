package foodwarehouse.core.data.message;

import foodwarehouse.core.data.employee.Employee;

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
