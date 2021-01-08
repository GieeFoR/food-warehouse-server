package foodwarehouse.web.response.maker;

import foodwarehouse.core.data.address.Address;

public record MakerResponse(
        int makerId,
        Address address,
        String firmName,
        String phoneNumber,
        String email) {
}
