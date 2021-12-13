package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.complaint.Complaint;
import foodwarehouse.core.data.complaint.ComplaintState;
import foodwarehouse.database.tables.CarTable;
import foodwarehouse.database.tables.ComplaintTable;
import foodwarehouse.database.tables.OrderTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

final public class ComplaintResultSetMapper implements ResultSetMapper<Complaint> {
    @Override
    public Complaint resultSetMap(ResultSet rs, String prefix) throws SQLException, ParseException {
        return new Complaint(
                rs.getInt(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.COMPLAINT_ID),
                new OrderResultSetMapper().resultSetMap(rs, prefix + ComplaintTable.NAME + "_"),
                rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.CONTENT),
                rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.SEND_DATE) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.SEND_DATE)),
                ComplaintState.valueOf(rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.STATE)),
                rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.DECISION).equals("") ? null : rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.DECISION),
                rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.DECISION_DATE) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.DECISION_DATE)));
    }
}
