package foodwarehouse.web.request.create.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.create.CreateAddressRequest;
import foodwarehouse.web.request.create.CreateUserRequest;

public record CreateCustomerRequest(
        @JsonProperty(value = "account", required = true)CreateUserRequest createUserRequest,
        @JsonProperty(value = "personal_data", required = true)   CreateCustomerData createCustomerData,
        @JsonProperty( value = "address", required = true)CreateAddressRequest createAddressRequest) {
}
