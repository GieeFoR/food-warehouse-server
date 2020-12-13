package foodwarehouse.core.complaint;

import foodwarehouse.core.order.Order;

import java.util.Date;

public record Complaint(
        int complaintId,
        Order order,
        String content,
        Date sendDate,
        String state,
        String decision,
        Date decisionDate) {
}
