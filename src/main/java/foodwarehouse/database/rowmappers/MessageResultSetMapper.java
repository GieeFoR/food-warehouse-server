package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.message.Message;
import foodwarehouse.database.tables.MessageTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageResultSetMapper implements ResultSetMapper<Message> {
    @Override
    public Message resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Message(
                rs.getInt(prefix+ MessageTable.NAME+"."+MessageTable.Columns.MESSAGE_ID),
                new EmployeeResultSetMapper().resultSetMap(rs, prefix+MessageTable.NAME+"_SENDER_"),
                new EmployeeResultSetMapper().resultSetMap(rs, prefix+MessageTable.NAME+"_RECEIVER_"),
                rs.getString(prefix+ MessageTable.NAME+"."+MessageTable.Columns.CONTENT),
                rs.getString(prefix+ MessageTable.NAME+"."+MessageTable.Columns.STATE),
                rs.getDate(prefix+ MessageTable.NAME+"."+MessageTable.Columns.SEND_DATE),
                rs.getDate(prefix+ MessageTable.NAME+"."+MessageTable.Columns.READ_DATE)
        );
    }
}
