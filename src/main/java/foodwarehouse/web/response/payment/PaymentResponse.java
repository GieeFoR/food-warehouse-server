package foodwarehouse.web.response.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.payment.Payment;

public record PaymentResponse(
        @JsonProperty(value = "payment_id", required = true)            int paymentId,
        @JsonProperty(value = "payment_type_id", required = true)       int paymentTypeId,
        @JsonProperty(value = "payment_type", required = true)          String paymentType,
        @JsonProperty(value = "value", required = true)                 float value,
        @JsonProperty(value = "payment_state", required = true)         String state) {

    public static PaymentResponse fromPayment(Payment payment) {
        return new PaymentResponse(
                payment.paymentId(),
                payment.paymentType().paymentTypeId(),
                payment.paymentType().type(),
                payment.value(),
                payment.state().value());
    }
}
