package foodwarehouse.core.database.rowmappers;

import foodwarehouse.core.database.tables.CustomerTable;
import foodwarehouse.core.user.customer.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerResultSetMapper implements ResultSetMapper<Customer> {

    @Override
    public Customer resultSetMap(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt(CustomerTable.Columns.CUSTOMER_ID),
                new UserResultSetMapper().resultSetMap(rs),
                new AddressResultSetMapper().resultSetMap(rs),
                rs.getString(CustomerTable.Columns.NAME),
                rs.getString(CustomerTable.Columns.SURNAME),
                rs.getString(CustomerTable.Columns.FIRMNAME),
                rs.getString(CustomerTable.Columns.PHONE),
                rs.getString(CustomerTable.Columns.TAX),
                rs.getInt(CustomerTable.Columns.DISCOUNT));
    }
}
