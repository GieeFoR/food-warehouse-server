package foodwarehouse.web.request.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateCustomerByCustomerData(
        @JsonProperty(value = "name", required = true)           String name,
        @JsonProperty(value = "surname", required = true)        String surname,
        @JsonProperty(value = "phone_number", required = true)   String phoneNumber,
        @JsonProperty(value = "firm_name")                       String firmName,
        @JsonProperty(value = "tax_id")                          String tax_id) {
}
