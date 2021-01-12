package foodwarehouse.web.request.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.address.UpdateAddressByCustomerRequest;
import foodwarehouse.web.request.user.UpdateUserByCustomerRequest;

public record UpdateCustomerByCustomerRequest(
        @JsonProperty(value = "account", required = true)           UpdateUserByCustomerRequest updateUserByCustomerRequest,
        @JsonProperty(value = "personal_data", required = true)     UpdateCustomerByCustomerData updateCustomerByCustomerData,
        @JsonProperty(value = "address", required = true)           UpdateAddressByCustomerRequest updateAddressByCustomerRequest) {
}
