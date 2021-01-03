package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.database.tables.StorageTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageResultSetMapper implements ResultSetMapper<Storage>{
    @Override
    public Storage resultSetMap(ResultSet rs) throws SQLException {
        return new Storage(
                rs.getInt(StorageTable.Columns.STORAGE_ID),
                new AddressResultSetMapper().resultSetMap(rs),
                new EmployeeResultSetMapper().resultSetMap(rs),
                rs.getString(StorageTable.Columns.STORAGE_NAME),
                rs.getInt(StorageTable.Columns.CAPACITY),
                rs.getBoolean(StorageTable.Columns.IS_COLD));
    }
}
