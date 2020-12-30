package foodwarehouse.core.database.rowmappers;

import foodwarehouse.core.database.tables.UserTable;
import foodwarehouse.core.user.Permission;
import foodwarehouse.core.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapper implements ResultSetMapper<User> {
    @Override
    public User resultSetMap(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt(UserTable.Columns.USER_ID),
                rs.getString(UserTable.Columns.USERNAME),
                rs.getString(UserTable.Columns.PASSWORD),
                rs.getString(UserTable.Columns.EMAIL),
                Permission.from(rs.getString(UserTable.Columns.PERMISSION)).get());
    }
}
