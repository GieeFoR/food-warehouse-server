package foodwarehouse.web.request.update.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.update.UpdateAddressRequest;
import foodwarehouse.web.request.update.UpdateUserRequest;

public record UpdateCustomerRequest(
        @JsonProperty(value = "account", required = true)UpdateUserRequest updateUserRequest,
        @JsonProperty(value = "personal_data", required = true)     UpdateCustomerData updateCustomerData,
        @JsonProperty(value = "address", required = true)UpdateAddressRequest updateAddressRequest) {
}
