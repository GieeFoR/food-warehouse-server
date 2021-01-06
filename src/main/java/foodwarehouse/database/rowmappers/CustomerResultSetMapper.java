package foodwarehouse.database.rowmappers;

import foodwarehouse.database.tables.CustomerTable;
import foodwarehouse.core.data.customer.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerResultSetMapper implements ResultSetMapper<Customer> {
    @Override
    public Customer resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Customer(
                rs.getInt(prefix+CustomerTable.NAME+"."+CustomerTable.Columns.CUSTOMER_ID),
                new UserResultSetMapper().resultSetMap(rs, prefix + CustomerTable.NAME+"_"),
                new AddressResultSetMapper().resultSetMap(rs, prefix + CustomerTable.NAME+"_"),
                rs.getString(prefix+CustomerTable.NAME+"."+CustomerTable.Columns.NAME),
                rs.getString(prefix+CustomerTable.NAME+"."+CustomerTable.Columns.SURNAME),
                rs.getString(prefix+CustomerTable.NAME+"."+CustomerTable.Columns.FIRMNAME),
                rs.getString(prefix+CustomerTable.NAME+"."+CustomerTable.Columns.PHONE),
                rs.getString(prefix+CustomerTable.NAME+"."+CustomerTable.Columns.TAX),
                rs.getInt(prefix+CustomerTable.NAME+"."+CustomerTable.Columns.DISCOUNT));
    }
}
