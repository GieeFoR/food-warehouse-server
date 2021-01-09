package foodwarehouse.web.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePaymentTypeRequest(
        @JsonProperty(value = "payment_type_id", required = true)   int paymentTypeId,
        @JsonProperty(value = "payment_type", required = true)      String type) {
}
