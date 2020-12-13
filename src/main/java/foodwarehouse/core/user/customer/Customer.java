package foodwarehouse.core.user.customer;

import foodwarehouse.core.user.User;
import foodwarehouse.core.address.Address;

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
}
