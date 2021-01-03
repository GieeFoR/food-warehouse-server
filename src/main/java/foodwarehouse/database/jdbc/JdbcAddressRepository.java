package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.address.AddressRepository;
import foodwarehouse.database.rowmappers.AddressResultSetMapper;
import foodwarehouse.database.tables.AddressTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
public class JdbcAddressRepository implements AddressRepository {

    private final String procedureInsertAddress = "CALL `INSERT_ADDRESS`(?,?,?,?,?,?,?)";

    private final String procedureUpdateAddress = "CALL `UPDATE_ADDRESS`(?,?,?,?,?,?,?)";

    private final String procedureReadAddresses = "CALL `GET_ADDRESSES`()";
    private final String procedureReadAddressById = "CALL `GET_ADDRESS_BY_ID`(?)";

    private final Connection connection;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcAddressRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Address> createAddress(String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureInsertAddress);
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

    @Override
    public Optional<Address> updateAddress(int addressId, String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureUpdateAddress);
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

    @Override
    public boolean deleteAddress(Address address) {
        String query = String.format("DELETE FROM `%s` WHERE `%s` = ?", AddressTable.NAME, AddressTable.Columns.ADDRESS_ID);
        Object[] args = new Object[] {address.addressId()};
        int delete = jdbcTemplate.update(query, args);

        if(delete == 1) {
            address = null;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Optional<Address> findAddressById(int addressId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(procedureReadAddressById);
        callableStatement.setInt(1, addressId);
        ResultSet resultSet = callableStatement.executeQuery();
        Address address = null;
        while(resultSet.next()) {
            address = new AddressResultSetMapper().resultSetMap(resultSet);
        }
        Optional<Address> optionalAddress = Optional.ofNullable(address);
        return optionalAddress;
    }
}
