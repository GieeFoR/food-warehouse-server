package foodwarehouse.web.response.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.complaint.Complaint;

import java.text.SimpleDateFormat;

public record ComplaintDataResponse(
        @JsonProperty(value = "complaint_id", required = true)          int complaintId,
        @JsonProperty(value = "content", required = true)               String content,
        @JsonProperty(value = "send_date", required = true)             String sendDate,
        @JsonProperty(value = "state", required = true)                 String state,
        @JsonProperty(value = "decision")                               String decision,
        @JsonProperty(value = "decision_date")                          String decisionDate) {

    public static ComplaintDataResponse fromComplaint(Complaint complaint) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ComplaintDataResponse(
                complaint.complaintId(),
                complaint.content(),
                sdf.format(complaint.sendDate()),
                complaint.state().value(),
                complaint.decision(),
                complaint.decisionDate() == null ? null : sdf.format(complaint.decisionDate()));
    }
}
