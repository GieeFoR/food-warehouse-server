package foodwarehouse.web.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePaymentRequest (
        @JsonProperty(value = "payment_type_id", required = true)           int paymentType,
        @JsonProperty(value = "value", required = true)                     float value){
}
