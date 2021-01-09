package foodwarehouse.core.data.complaint;

import foodwarehouse.core.data.order.Order;

import java.util.Date;

public record Complaint(
        int complaintId,
        Order order,
        String content,
        Date sendDate,
        ComplaintState state,
        String decision,
        Date decisionDate) {
}
