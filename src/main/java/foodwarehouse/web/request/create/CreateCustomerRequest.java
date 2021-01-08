package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCustomerRequest(
        @JsonProperty(value = "account", required = true)         CreateUserRequest createUserRequest,
        @JsonProperty(value = "personal_data", required = true)   CreateCustomerData createCustomerData,
        @JsonProperty( value = "address", required = true)        CreateAddressRequest createAddressRequest) {
}
