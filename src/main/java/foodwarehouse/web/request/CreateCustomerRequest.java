package foodwarehouse.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.user.Account;
import foodwarehouse.core.user.customer.CustomerPersonalData;
import foodwarehouse.core.address.Address;

public record CreateCustomerRequest(
        @JsonProperty("account")        Account account,
        @JsonProperty("personal_data")  CustomerPersonalData customerPersonalData,
        @JsonProperty("address")        Address address){
}
