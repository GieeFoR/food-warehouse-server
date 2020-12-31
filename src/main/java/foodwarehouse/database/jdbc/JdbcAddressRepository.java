package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.address.AddressRepository;
import foodwarehouse.database.rowmappers.AddressResultSetMapper;
import foodwarehouse.database.tables.AddressTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.Optional;

@Repository
public class JdbcAddressRepository implements AddressRepository {

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
    public Optional<Address> createAddress(String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) {
        String query = String.format("INSERT INTO `%s`(`%s`, `%s`, `%s`, `%s`, `%s`, `%s`) VALUES (?,?,?,?,?,?)",
                AddressTable.NAME,
                AddressTable.Columns.COUNTRY,
                AddressTable.Columns.TOWN,
                AddressTable.Columns.POSTAL_CODE,
                AddressTable.Columns.BUILDING_NUMBER,
                AddressTable.Columns.STREET,
                AddressTable.Columns.APARTMENT_NUMBER);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, country);
                ps.setString(2, town);
                ps.setString(3, postalCode);
                ps.setString(4, buildingNumber);
                ps.setString(5, street);
                ps.setString(6, apartmentNumber);
                return ps;
            }, keyHolder);

            BigInteger biguid = (BigInteger) keyHolder.getKey();
            int addressId = biguid.intValue();
            return Optional.of(new Address(addressId, country, town, postalCode, buildingNumber, street, apartmentNumber));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean updateAddress(Address address, String country, String town, String postalCode, String buildingNumber, String street, String apartmentNumber) {
        return false;
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
