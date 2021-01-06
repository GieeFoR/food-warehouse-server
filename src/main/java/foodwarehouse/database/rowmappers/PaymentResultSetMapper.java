package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.database.tables.PaymentTable;
import foodwarehouse.database.tables.PaymentTypeTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentResultSetMapper implements ResultSetMapper<Payment> {
    @Override
    public Payment resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Payment(
                rs.getInt(prefix+PaymentTable.NAME+"."+PaymentTable.Columns.PAYMENT_ID),
                new PaymentTypeResultSetMapper().resultSetMap(rs, prefix + PaymentTable.NAME + "_"),
                rs.getFloat(prefix+PaymentTable.NAME+"."+PaymentTable.Columns.VALUE),
                rs.getString(prefix+PaymentTable.NAME+"."+PaymentTable.Columns.STATE));
    }
}
