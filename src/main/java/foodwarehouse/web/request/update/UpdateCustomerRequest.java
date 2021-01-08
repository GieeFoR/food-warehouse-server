package foodwarehouse.web.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.customer.CustomerPersonalData;

public record UpdateCustomerRequest(
        @JsonProperty(value = "user_id", required = true)           int userId,
        @JsonProperty(value = "address_id", required = true)        int addressId,
        @JsonProperty(value = "customer_id", required = true)       int customerId,
        @JsonProperty(value = "account", required = true)           UpdateUserRequest updateUserRequest,
        @JsonProperty(value = "personal_data", required = true)     CustomerPersonalData customerPersonalData,
        @JsonProperty(value = "address", required = true)           UpdateAddressRequest updateAddressRequest) {
}
