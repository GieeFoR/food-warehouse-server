package foodwarehouse.web.response.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.customer.Customer;

public record CustomerDataResponse(
        @JsonProperty(value = "customer_id", required = true)    int customerId,
        @JsonProperty(value = "name", required = true)           String name,
        @JsonProperty(value = "surname", required = true)        String surname,
        @JsonProperty(value = "phone_number", required = true)   String phoneNumber,
        @JsonProperty(value = "firm_name")                       String firmName,
        @JsonProperty(value = "tax_id")                          String tax_id) {

    public static CustomerDataResponse fromCustomer(Customer customer) {
        return new CustomerDataResponse(
                customer.customerId(),
                customer.name(),
                customer.surname(),
                customer.phoneNumber(),
                customer.firmName(),
                customer.taxId());
    }
}
