package foodwarehouse.web.request.create.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.customer.Customer;

public record CreateCustomerData(
        @JsonProperty(value = "name", required = true)           String name,
        @JsonProperty(value = "surname", required = true)        String surname,
        @JsonProperty(value = "phone_number", required = true)   String phoneNumber,
        @JsonProperty(value = "firm_name")      String firmName,
        @JsonProperty(value = "tax_id")         String tax_id) {

    public static CreateCustomerData fromCustomer(Customer customer) {
        return new CreateCustomerData(
                customer.name(),
                customer.surname(),
                customer.phoneNumber(),
                customer.firmName(),
                customer.taxId());
    }
}
