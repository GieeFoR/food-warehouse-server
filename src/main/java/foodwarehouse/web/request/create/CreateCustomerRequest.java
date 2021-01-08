package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.customer.CustomerPersonalData;

public record CreateCustomerRequest(
        @JsonProperty(value = "account", required = true)         CreateUserRequest createUserRequest,
        @JsonProperty(value = "personal_data", required = true)   CustomerPersonalData customerPersonalData,
        @JsonProperty( value = "address", required = true)        CreateAddressRequest createAddressRequest) {
}
