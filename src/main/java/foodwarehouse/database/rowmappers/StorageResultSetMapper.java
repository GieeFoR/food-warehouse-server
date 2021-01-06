package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.database.tables.StorageTable;
import foodwarehouse.database.tables.UserTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageResultSetMapper implements ResultSetMapper<Storage>{
    @Override
    public Storage resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Storage(
                rs.getInt(prefix+StorageTable.NAME+"."+StorageTable.Columns.STORAGE_ID),
                new AddressResultSetMapper().resultSetMap(rs, prefix + StorageTable.NAME + "_"),
                new EmployeeResultSetMapper().resultSetMap(rs,prefix + StorageTable.NAME + "_"),
                rs.getString(prefix+StorageTable.NAME+"."+StorageTable.Columns.STORAGE_NAME),
                rs.getInt(prefix+StorageTable.NAME+"."+StorageTable.Columns.CAPACITY),
                rs.getBoolean(prefix+StorageTable.NAME+"."+StorageTable.Columns.IS_COLD));
    }
}
