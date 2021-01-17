package foodwarehouse.web.response.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.complaint.Complaint;

import java.text.SimpleDateFormat;

public record ComplaintResponse(
        @JsonProperty(value = "complaint_id", required = true)          int complaintId,
        @JsonProperty(value = "order_id", required = true)              int orderId,
        @JsonProperty(value = "value", required = true)                 float value,
        @JsonProperty(value = "order_date", required = true)            String orderDate,
        @JsonProperty(value = "content", required = true)               String content,
        @JsonProperty(value = "send_date", required = true)             String sendDate,
        @JsonProperty(value = "state", required = true)                 String state,
        @JsonProperty(value = "decision")                               String decision,
        @JsonProperty(value = "decision_date")                          String decisionDate) {

    public static ComplaintResponse fromComplaint(Complaint complaint) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ComplaintResponse(
                complaint.complaintId(),
                complaint.order().orderId(),
                complaint.order().payment().value(),
                sdf.format(complaint.order().orderDate()),
                complaint.content(),
                sdf.format(complaint.sendDate()),
                complaint.state().value(),
                complaint.decision(),
                complaint.decisionDate() == null ? null : sdf.format(complaint.decisionDate()));
    }
}
