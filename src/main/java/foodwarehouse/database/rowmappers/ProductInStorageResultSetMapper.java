package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.database.tables.ProductInStorageTable;

import java.sql.ResultSet;
import java.sql.SQLException;

final public class ProductInStorageResultSetMapper implements ResultSetMapper<ProductInStorage> {
    @Override
    public ProductInStorage resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new ProductInStorage(
                new ProductBatchResultSetMapper().resultSetMap(rs, prefix + ProductInStorageTable.NAME + "_"),
                new StorageResultSetMapper().resultSetMap(rs, prefix + ProductInStorageTable.NAME + "_"),
                rs.getInt(ProductInStorageTable.Columns.QUANTITY));
    }
}
