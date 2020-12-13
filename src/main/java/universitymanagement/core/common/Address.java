package universitymanagement.core.common;

public record Address(
        int addressId,
        String street,
        String houseNumber,
        String apartmentNumber,
        String postcode,
        String town,
        String country) {
}
