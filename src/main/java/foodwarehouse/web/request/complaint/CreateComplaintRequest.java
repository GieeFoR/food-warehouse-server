package foodwarehouse.web.request.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateComplaintRequest(
        @JsonProperty(value = "order_id", required = true)      int orderId,
        @JsonProperty(value = "content", required = true)       String content) {
}
