package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.address.AddressRepository;
import foodwarehouse.database.rowmappers.AddressResultSetMapper;
import foodwarehouse.database.tables.AddressTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
public class JdbcAddressRepository implements AddressRepository {

    private final Connection connection;

    @Autowired
    JdbcAddressRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<Address> createAddress(String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) {
        try {
            CallableStatement callableStatement = connection.prepareCall(AddressTable.Procedures.INSERT);
            callableStatement.setString(1, country);
            callableStatement.setString(2, town);
            callableStatement.setString(3, postalCode);
            callableStatement.setString(4, buildingNumber);
            callableStatement.setString(5, street);
            callableStatement.setString(6, apartmentNumber);

            callableStatement.executeQuery();
            int addressId = callableStatement.getInt(7);
            return Optional.of(new Address(addressId, country, town, postalCode, buildingNumber, street, apartmentNumber));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Address> updateAddress(int addressId, String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) {
        try {
            CallableStatement callableStatement = connection.prepareCall(AddressTable.Procedures.UPDATE);
            callableStatement.setInt(1, addressId);
            callableStatement.setString(2, country);
            callableStatement.setString(3, town);
            callableStatement.setString(4, postalCode);
            callableStatement.setString(5, buildingNumber);
            callableStatement.setString(6, street);
            callableStatement.setString(7, apartmentNumber);

            callableStatement.executeQuery();

            return Optional.of(new Address(addressId, country, town, postalCode, buildingNumber, street, apartmentNumber));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteAddress(int addressId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(AddressTable.Procedures.DELETE);
            callableStatement.setInt(1, addressId);

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<Address> findAddressById(int addressId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(AddressTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, addressId);

            ResultSet resultSet = callableStatement.executeQuery();
            Address address = null;
            if(resultSet.next()) {
                address = new AddressResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(address);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }
}
