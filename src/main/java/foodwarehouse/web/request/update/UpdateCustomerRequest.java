package foodwarehouse.web.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateCustomerRequest(
        @JsonProperty(value = "account", required = true)           UpdateUserRequest updateUserRequest,
        @JsonProperty(value = "personal_data", required = true)     UpdateCustomerData updateCustomerData,
        @JsonProperty(value = "address", required = true)           UpdateAddressRequest updateAddressRequest) {
}
