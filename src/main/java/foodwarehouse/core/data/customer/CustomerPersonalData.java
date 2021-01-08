package foodwarehouse.core.data.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerPersonalData(
        @JsonProperty("name")           String name,
        @JsonProperty("surname")        String surname,
        @JsonProperty("phone_number")   String phoneNumber,
        @JsonProperty("firm_name")      String firmName,
        @JsonProperty("tax_id")         String tax_id) {

    public static CustomerPersonalData fromCustomer(Customer customer) {
        return new CustomerPersonalData(
                customer.name(),
                customer.surname(),
                customer.phoneNumber(),
                customer.firmName(),
                customer.taxId());
    }
}
