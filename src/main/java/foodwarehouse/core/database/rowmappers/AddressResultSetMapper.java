package foodwarehouse.core.database.rowmappers;

import foodwarehouse.core.address.Address;
import foodwarehouse.core.database.tables.AddressTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressResultSetMapper implements ResultSetMapper<Address>{
    @Override
    public Address resultSetMap(ResultSet rs) throws SQLException {
        return new Address(
                rs.getInt(AddressTable.Columns.ADDRESS_ID),
                rs.getString(AddressTable.Columns.COUNTRY),
                rs.getString(AddressTable.Columns.TOWN),
                rs.getString(AddressTable.Columns.POSTAL_CODE),
                rs.getString(AddressTable.Columns.BUILDING_NUMBER),
                rs.getString(AddressTable.Columns.STREET),
                rs.getString(AddressTable.Columns.APARTMENT_NUMBER));
    }
}
