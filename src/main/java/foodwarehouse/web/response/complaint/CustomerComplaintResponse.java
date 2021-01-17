package foodwarehouse.web.response.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.complaint.Complaint;

import java.text.SimpleDateFormat;
import java.util.Date;

public record CustomerComplaintResponse(
        @JsonProperty(value = "complaint_id", required = true)          int complaintId,
        @JsonProperty(value = "content", required = true)               String content,
        @JsonProperty(value = "send_date", required = true)             String sendDate,
        @JsonProperty(value = "state", required = true)                 String state,
        @JsonProperty(value = "decision")                               String decision,
        @JsonProperty(value = "decision_date")                          String decisionDate) {

    public static CustomerComplaintResponse fromComplaint(Complaint complaint) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new CustomerComplaintResponse(
                complaint.complaintId(),
                complaint.content(),
                sdf.format(complaint.sendDate()),
                complaint.state().value(),
                complaint.decision(),
                sdf.format(complaint.decisionDate()));
    }
}
