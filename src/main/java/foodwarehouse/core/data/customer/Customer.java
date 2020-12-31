package foodwarehouse.core.data.customer;

import foodwarehouse.core.data.user.User;
import foodwarehouse.core.data.address.Address;

public record Customer(
        int customerId,
        User user,
        Address address,
        String name,
        String surname,
        String firmName,
        String phoneNumber,
        String taxId,
        int discount) {

    public static CustomerPersonalData toCustomerPersonalData(Customer customer) {
        return new CustomerPersonalData(
                customer.customerId(),
                customer.name(),
                customer.surname(),
                customer.phoneNumber(),
                customer.firmName(),
                customer.taxId());
    }
}
