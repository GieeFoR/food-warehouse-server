package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.customer.CustomerPersonalData;

public record CustomerResponse(
        @JsonProperty("account") UserResponse userResponse,
        @JsonProperty("personal_data") CustomerPersonalData customerPersonalData,
        @JsonProperty("address") Address address
) {
}
