package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.database.tables.AddressTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressResultSetMapper implements ResultSetMapper<Address> {
    @Override
    public Address resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Address(
                rs.getInt(prefix+AddressTable.NAME+"."+AddressTable.Columns.ADDRESS_ID),
                rs.getString(prefix+AddressTable.NAME+"."+AddressTable.Columns.COUNTRY),
                rs.getString(prefix+AddressTable.NAME+"."+AddressTable.Columns.TOWN),
                rs.getString(prefix+AddressTable.NAME+"."+AddressTable.Columns.POSTAL_CODE),
                rs.getString(prefix+AddressTable.NAME+"."+AddressTable.Columns.BUILDING_NUMBER),
                rs.getString(prefix+AddressTable.NAME+"."+AddressTable.Columns.STREET),
                rs.getString(prefix+AddressTable.NAME+"."+AddressTable.Columns.APARTMENT_NUMBER));
    }
}
