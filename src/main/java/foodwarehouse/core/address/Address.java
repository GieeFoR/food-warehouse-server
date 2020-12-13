package foodwarehouse.core.address;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Address(
        @JsonProperty("address_id") int addressId,
        @JsonProperty("country") String country,
        @JsonProperty("town") String town,
        @JsonProperty("postal_code") String postalCode,
        @JsonProperty("building_number") String buildingNumber,
        @JsonProperty("street") String street,
        @JsonProperty("apartment_number") String apartmentNumber) {
}
