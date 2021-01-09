package foodwarehouse.web.response.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.address.Address;

public record AddressResponse(
        @JsonProperty(value = "address_id", required = true)         int addressId,
        @JsonProperty(value = "country", required = true)            String country,
        @JsonProperty(value = "town", required = true)               String town,
        @JsonProperty(value = "postal_code", required = true)        String postalCode,
        @JsonProperty(value = "building_number", required = true)    String buildingNumber,
        @JsonProperty(value = "street")                              String street,
        @JsonProperty(value = "apartment_number")                    String apartmentNumber) {

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
