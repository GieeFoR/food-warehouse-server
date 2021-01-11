package foodwarehouse.web.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.customer.Customer;

public record NameResponse(
        @JsonProperty(value = "name", required = true)      String name,
        @JsonProperty(value = "surname", required = true)   String surname) {

    public static NameResponse fromCustomer(Customer customer) {
        return new NameResponse(
                customer.name(),
                customer.surname());
    }
}
