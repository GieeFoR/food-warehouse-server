package foodwarehouse.web.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePaymentValueRequest(
        @JsonProperty(value = "payment_id", required = true)            int paymentId,
        @JsonProperty(value = "value", required = true)                 float value) {
}
