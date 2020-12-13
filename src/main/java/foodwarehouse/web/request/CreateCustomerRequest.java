package foodwarehouse.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.user.Account;
import foodwarehouse.web.user.PersonalData;
import universitymanagement.core.common.Address;

public record CreateCustomerRequest(
        @JsonProperty("account")Account account,
        @JsonProperty("personal_data")PersonalData personalData,
        @JsonProperty("address")        Address address){
}
