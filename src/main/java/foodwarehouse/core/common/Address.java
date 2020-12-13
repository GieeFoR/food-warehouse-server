package foodwarehouse.core.common;

public record Address(
        int addressId,
        String country,
        String town,
        String postalCode,
        String buildingNumber,
        String street,
        String apartmentNumber) {
}
