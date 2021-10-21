package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.address.AddressRepository;
import foodwarehouse.database.rowmappers.AddressResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.AddressTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.io.FileNotFoundException;
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
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("address"), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, country);
            statement.setString(2, town);
            statement.setString(3, postalCode);
            statement.setString(4, buildingNumber);
            statement.setString(5, street);
            statement.setString(6, apartmentNumber);
            statement.executeUpdate();
            int addressId = statement.getGeneratedKeys().getInt(1);

            return Optional.of(new Address(addressId, country, town, postalCode, buildingNumber, street, apartmentNumber));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Address> updateAddress(int addressId, String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("address"));
            statement.setString(1, country);
            statement.setString(2, town);
            statement.setString(3, postalCode);
            statement.setString(4, buildingNumber);
            statement.setString(5, street);
            statement.setString(6, apartmentNumber);
            statement.setInt(7, addressId);

            statement.executeUpdate();

            return Optional.of(new Address(addressId, country, town, postalCode, buildingNumber, street, apartmentNumber));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteAddress(int addressId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("address"));
            statement.setInt(1, addressId);
            statement.executeUpdate();

            return true;
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<Address> findAddressById(int addressId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("address_byId"));
            statement.setInt(1, addressId);
            ResultSet resultSet = statement.executeQuery();

            Address address = null;
            if(resultSet.next()) {
                address = new AddressResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(address);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }
}
