package foodwarehouse.database.rowmappers;

import foodwarehouse.database.tables.UserTable;
import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapper implements ResultSetMapper<User> {

    @Override
    public User resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new User(
                rs.getInt(prefix+UserTable.NAME+"."+UserTable.Columns.USER_ID),
                rs.getString(prefix+UserTable.NAME+"."+UserTable.Columns.USERNAME),
                rs.getString(prefix+UserTable.NAME+"."+UserTable.Columns.PASSWORD),
                rs.getString(prefix+UserTable.NAME+"."+UserTable.Columns.EMAIL),
                Permission.from(rs.getString(prefix+UserTable.NAME+"."+UserTable.Columns.PERMISSION)).get());
    }
}
