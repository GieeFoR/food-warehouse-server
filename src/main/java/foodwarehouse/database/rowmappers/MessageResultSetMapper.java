package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.message.Message;
import foodwarehouse.database.tables.MessageTable;
import foodwarehouse.database.tables.ProductBatchTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

final public class MessageResultSetMapper implements ResultSetMapper<Message> {
    @Override
    public Message resultSetMap(ResultSet rs, String prefix) throws SQLException, ParseException {
        return new Message(
                rs.getInt(prefix+ MessageTable.NAME+"."+MessageTable.Columns.MESSAGE_ID),
                new EmployeeResultSetMapper().resultSetMap(rs, prefix+MessageTable.NAME+"_SENDER_"),
                new EmployeeResultSetMapper().resultSetMap(rs, prefix+MessageTable.NAME+"_RECEIVER_"),
                rs.getString(prefix+ MessageTable.NAME+"."+MessageTable.Columns.CONTENT),
                rs.getString(prefix+ MessageTable.NAME+"."+MessageTable.Columns.STATE),
                rs.getString(prefix+ MessageTable.NAME+"."+MessageTable.Columns.SEND_DATE) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(prefix+ MessageTable.NAME+"."+MessageTable.Columns.SEND_DATE)),
                rs.getString(prefix+ MessageTable.NAME+"."+MessageTable.Columns.READ_DATE) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(prefix+ MessageTable.NAME+"."+MessageTable.Columns.READ_DATE)));
    }
}
