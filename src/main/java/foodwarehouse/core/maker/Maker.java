package foodwarehouse.core.maker;

import universitymanagement.core.common.Address;

public record Maker(
        int makerId,
        Address address,
        String firmName,
        String phoneNumber,
        String email) {
}
