package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePaymentTypeRequest(
        @JsonProperty(value = "payment_type", required = true)      String type) {
}
