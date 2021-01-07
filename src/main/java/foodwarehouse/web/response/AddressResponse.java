package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.address.Address;

public record AddressResponse(
        @JsonProperty("address_id")         int addressId,
        @JsonProperty("country")            String country,
        @JsonProperty("town")               String town,
        @JsonProperty("postal_code")        String postalCode,
        @JsonProperty("building_number")    String buildingNumber,
        @JsonProperty("street")             String street,
        @JsonProperty("apartment_number")   String apartmentNumber) {

    public static AddressResponse fromAddress(Address address) {
        return new AddressResponse(
                address.addressId(),
                address.country(),
                address.town(),
                address.postalCode(),
                address.buildingNumber(),
                address.street(),
                address.apartmentNumber());
    }
}
