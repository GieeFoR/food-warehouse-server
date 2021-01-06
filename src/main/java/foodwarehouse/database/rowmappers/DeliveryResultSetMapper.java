package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.database.tables.DeliveryTable;

import java.sql.ResultSet;
import java.sql.SQLException;

final public class DeliveryResultSetMapper implements ResultSetMapper<Delivery> {

    @Override
    public Delivery resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Delivery(
                rs.getInt(prefix+DeliveryTable.NAME+"."+DeliveryTable.Columns.DELIVERY_ID),
                new AddressResultSetMapper().resultSetMap(rs, prefix + DeliveryTable.NAME + "_"),
                new EmployeeResultSetMapper().resultSetMap(rs,prefix + DeliveryTable.NAME + "_"),
                rs.getDate(prefix+DeliveryTable.NAME+"."+DeliveryTable.Columns.REMOVAL_DATE),
                rs.getDate(prefix+DeliveryTable.NAME+"."+DeliveryTable.Columns.DELIVERY_DATE));
    }
}
