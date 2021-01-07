package foodwarehouse.core.data.address;

import java.util.Optional;

public interface AddressRepository {

    Optional<Address> createAddress(String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber);

    Optional<Address> updateAddress(int addressId, String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber);

    boolean deleteAddress(int addressId);

    Optional<Address> findAddressById(int addressId);
}
