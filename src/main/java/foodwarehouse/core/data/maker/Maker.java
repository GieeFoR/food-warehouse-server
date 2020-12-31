package foodwarehouse.core.data.maker;

import foodwarehouse.core.data.address.Address;

public record Maker(
        int makerId,
        Address address,
        String firmName,
        String phoneNumber,
        String email) {
}
