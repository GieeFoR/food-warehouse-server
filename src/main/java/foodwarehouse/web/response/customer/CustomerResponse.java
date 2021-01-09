package foodwarehouse.web.response.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.web.response.address.AddressResponse;
import foodwarehouse.web.response.user.UserResponse;

public record CustomerResponse(
        @JsonProperty(value = "account", required = true)           UserResponse userResponse,
        @JsonProperty(value = "personal_data", required = true)     CustomerDataResponse customerDataResponse,
        @JsonProperty(value = "address", required = true)           AddressResponse addressResponse) {

    public static CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                        UserResponse.fromUser(customer.user()),
                        CustomerDataResponse.fromCustomer(customer),
                        AddressResponse.fromAddress(customer.address()));
    }
}
