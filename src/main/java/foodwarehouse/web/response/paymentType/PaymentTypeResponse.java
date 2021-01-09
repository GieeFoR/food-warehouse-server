package foodwarehouse.web.response.paymentType;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.paymentType.PaymentType;

public record PaymentTypeResponse(
        @JsonProperty(value = "payment_type_id", required = true)   int paymentTypeId,
        @JsonProperty(value = "payment_type", required = true)      String type) {

    public static PaymentTypeResponse fromPaymentType(PaymentType paymentType) {
        return new PaymentTypeResponse(
                paymentType.paymentTypeId(),
                paymentType.type());
    }
}
