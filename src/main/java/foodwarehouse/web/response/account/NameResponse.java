package foodwarehouse.web.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.employee.Employee;

public record NameResponse(
        @JsonProperty(value = "name", required = true)      String name,
        @JsonProperty(value = "surname", required = true)   String surname) {

    public static NameResponse fromCustomer(Customer customer) {
        return new NameResponse(
                customer.name(),
                customer.surname());
    }

    public static NameResponse fromEmployee(Employee employee) {
        return new NameResponse(
                employee.name(),
                employee.surname());
    }
}
