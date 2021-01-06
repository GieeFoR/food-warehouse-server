package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.database.tables.PaymentTypeTable;

import java.sql.ResultSet;
import java.sql.SQLException;

final public class PaymentTypeResultSetMapper implements ResultSetMapper<PaymentType> {
    @Override
    public PaymentType resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new PaymentType(
                rs.getInt(prefix+PaymentTypeTable.NAME+"."+PaymentTypeTable.Columns.TYPE_ID),
                rs.getString(prefix+PaymentTypeTable.NAME+"."+PaymentTypeTable.Columns.TYPE));
    }
}
