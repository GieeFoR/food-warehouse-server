package foodwarehouse.core.data.address;

import java.sql.SQLException;
import java.util.Optional;

public interface AddressRepository {

    Optional<Address> createAddress(String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) throws SQLException;

    Optional<Address> updateAddress(int addressId, String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) throws SQLException;

    boolean deleteAddress(Address address);

    Optional<Address> findAddressById(int addressId) throws SQLException;

}
