package foodwarehouse.web.request.address;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateAddressRequest(
        @JsonProperty(value = "country", required = true)            String country,
        @JsonProperty(value = "town", required = true)               String town,
        @JsonProperty(value = "postal_code", required = true)        String postalCode,
        @JsonProperty(value = "building_number", required = true)    String buildingNumber,
        @JsonProperty(value = "street")                              String street,
        @JsonProperty(value = "apartment_number")                    String apartmentNumber) {
}
