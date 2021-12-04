package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.database.tables.DeliveryTable;
import foodwarehouse.database.tables.OrderTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

final public class DeliveryResultSetMapper implements ResultSetMapper<Delivery> {

    @Override
    public Delivery resultSetMap(ResultSet rs, String prefix) throws SQLException, ParseException {
        return new Delivery(
                rs.getInt(prefix+DeliveryTable.NAME+"."+DeliveryTable.Columns.DELIVERY_ID),
                new AddressResultSetMapper().resultSetMap(rs, prefix + DeliveryTable.NAME + "_"),
                new EmployeeResultSetMapper().resultSetMap(rs,prefix + DeliveryTable.NAME + "_"),
                rs.getString(prefix+DeliveryTable.NAME+"."+DeliveryTable.Columns.REMOVAL_DATE) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(prefix+DeliveryTable.NAME+"."+DeliveryTable.Columns.REMOVAL_DATE)),
                rs.getString(prefix+DeliveryTable.NAME+"."+DeliveryTable.Columns.DELIVERY_DATE) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(prefix+DeliveryTable.NAME+"."+DeliveryTable.Columns.DELIVERY_DATE)));
    }
}
