package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentState;
import foodwarehouse.database.tables.PaymentTable;

import java.sql.ResultSet;
import java.sql.SQLException;

final public class PaymentResultSetMapper implements ResultSetMapper<Payment> {
    @Override
    public Payment resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Payment(
                rs.getInt(prefix+PaymentTable.NAME+"."+PaymentTable.Columns.PAYMENT_ID),
                new PaymentTypeResultSetMapper().resultSetMap(rs, prefix + PaymentTable.NAME + "_"),
                rs.getFloat(prefix+PaymentTable.NAME+"."+PaymentTable.Columns.VALUE),
                PaymentState.from(rs.getString(prefix+PaymentTable.NAME+"."+PaymentTable.Columns.STATE)).get());
    }
}
