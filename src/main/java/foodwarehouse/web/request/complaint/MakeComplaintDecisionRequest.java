package foodwarehouse.web.request.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MakeComplaintDecisionRequest(
        @JsonProperty(value = "complaint_id", required = true)                  int complaintId,
        @JsonProperty(value = "decision_state", required = true)                String complaintState,
        @JsonProperty(value = "decision_content", required = true)              String decision
) {
}
