package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.complaint.Complaint;

import java.util.Date;

public record ComplaintResponse(
        @JsonProperty(value = "complaint_id", required = true)          int complaintId,
        @JsonProperty(value = "content", required = true)               String content,
        @JsonProperty(value = "send_date", required = true)             Date sendDate,
        @JsonProperty(value = "state", required = true)                 String state,
        @JsonProperty(value = "decision")                               String decision,
        @JsonProperty(value = "decision_date")                          Date decisionDate) {

    public static ComplaintResponse fromComplaint(Complaint complaint) {
        return new ComplaintResponse(
                complaint.complaintId(),
                complaint.content(),
                complaint.sendDate(),
                complaint.state().value(),
                complaint.decision(),
                complaint.decisionDate());
    }
}
