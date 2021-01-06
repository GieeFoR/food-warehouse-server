package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.database.tables.OrderTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderResultSetMapper implements ResultSetMapper<Order> {

    @Override
    public Order resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Order(
                rs.getInt(prefix+OrderTable.NAME+"."+OrderTable.Columns.ORDER_ID),
                new PaymentResultSetMapper().resultSetMap(rs, prefix + OrderTable.NAME + "_"),
                new CustomerResultSetMapper().resultSetMap(rs,prefix + OrderTable.NAME + "_"),
                new DeliveryResultSetMapper().resultSetMap(rs,prefix + OrderTable.NAME + "_"),
                rs.getString(prefix+OrderTable.NAME+"."+OrderTable.Columns.COMMENT),
                rs.getString(prefix+OrderTable.NAME+"."+OrderTable.Columns.ORDER_STATE));
    }
}
