package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.customer.CustomerPersonalData;

public record CustomerResponse(
        @JsonProperty("account")            UserResponse userResponse,
        @JsonProperty("personal_data")      CustomerPersonalData customerPersonalData,
        @JsonProperty("address")            AddressResponse addressResponse) {

    public static CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                        UserResponse.fromUser(customer.user()),
                        CustomerPersonalData.fromCustomer(customer),
                        AddressResponse.fromAddress(customer.address()));
    }
}
