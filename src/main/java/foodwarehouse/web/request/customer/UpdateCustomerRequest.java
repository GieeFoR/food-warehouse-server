package foodwarehouse.web.request.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.address.UpdateAddressRequest;
import foodwarehouse.web.request.user.UpdateUserRequest;

public record UpdateCustomerRequest(
        @JsonProperty(value = "account", required = true)UpdateUserRequest updateUserRequest,
        @JsonProperty(value = "personal_data", required = true)     UpdateCustomerData updateCustomerData,
        @JsonProperty(value = "address", required = true)UpdateAddressRequest updateAddressRequest) {
}
