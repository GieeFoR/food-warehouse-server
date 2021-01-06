package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.complaint.Complaint;
import foodwarehouse.database.tables.ComplaintTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComplaintResultSetMapper implements ResultSetMapper<Complaint> {
    @Override
    public Complaint resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Complaint(
                rs.getInt(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.COMPLAINT_ID),
                new OrderResultSetMapper().resultSetMap(rs, prefix + ComplaintTable.NAME + "_"),
                rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.CONTENT),
                rs.getDate(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.SEND_DATE),
                rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.STATE),
                rs.getString(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.DECISION),
                rs.getDate(prefix+ComplaintTable.NAME+"."+ComplaintTable.Columns.DECISION_DATE));
    }
}
