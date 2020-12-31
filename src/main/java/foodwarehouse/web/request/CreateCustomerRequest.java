package foodwarehouse.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.user.Account;
import foodwarehouse.core.data.customer.CustomerPersonalData;
import foodwarehouse.core.data.address.Address;

public record CreateCustomerRequest(
        @JsonProperty("account")        Account account,
        @JsonProperty("personal_data")  CustomerPersonalData customerPersonalData,
        @JsonProperty("address")        Address address){
}
