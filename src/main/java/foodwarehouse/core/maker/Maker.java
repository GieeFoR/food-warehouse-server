package foodwarehouse.core.maker;

import foodwarehouse.core.address.Address;

public record Maker(
        int makerId,
        Address address,
        String firmName,
        String phoneNumber,
        String email) {
}
