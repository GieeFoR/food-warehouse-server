package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.database.tables.ProductInStorageTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

final public class ProductInStorageResultSetMapper implements ResultSetMapper<ProductInStorage> {
    @Override
    public ProductInStorage resultSetMap(ResultSet rs, String prefix) throws SQLException, ParseException {
        return new ProductInStorage(
                new ProductBatchResultSetMapper().resultSetMap(rs, prefix + ProductInStorageTable.NAME + "_"),
                new StorageResultSetMapper().resultSetMap(rs, prefix + ProductInStorageTable.NAME + "_"),
                rs.getInt(prefix+ProductInStorageTable.NAME+"."+ProductInStorageTable.Columns.QUANTITY));
    }
}
