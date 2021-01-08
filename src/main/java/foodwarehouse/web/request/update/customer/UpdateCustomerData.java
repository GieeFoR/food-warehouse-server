package foodwarehouse.web.request.update.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.customer.Customer;

public record UpdateCustomerData(
        @JsonProperty(value = "customer_id", required = true)    int customerId,
        @JsonProperty(value = "name", required = true)           String name,
        @JsonProperty(value = "surname", required = true)        String surname,
        @JsonProperty(value = "phone_number", required = true)   String phoneNumber,
        @JsonProperty(value = "firm_name")                       String firmName,
        @JsonProperty(value = "tax_id")                          String tax_id) {

    public static UpdateCustomerData fromCustomer(Customer customer) {
        return new UpdateCustomerData(
                customer.customerId(),
                customer.name(),
                customer.surname(),
                customer.phoneNumber(),
                customer.firmName(),
                customer.taxId());
    }
}
