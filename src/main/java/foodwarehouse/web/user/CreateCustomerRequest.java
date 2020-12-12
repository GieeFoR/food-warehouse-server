package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import universitymanagement.core.common.Address;

public record CreateCustomerRequest(
        @JsonProperty("account")        Account account,
        @JsonProperty("personal_data")  PersonalData personalData,
        @JsonProperty("address")        Address address){
}
