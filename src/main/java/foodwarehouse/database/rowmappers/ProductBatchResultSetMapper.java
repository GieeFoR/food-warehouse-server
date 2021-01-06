package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.database.tables.ProductBatchTable;

import java.sql.ResultSet;
import java.sql.SQLException;

final public class ProductBatchResultSetMapper implements ResultSetMapper<ProductBatch> {
    @Override
    public ProductBatch resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new ProductBatch(
                rs.getInt(prefix+ProductBatchTable.NAME+"."+ProductBatchTable.Columns.BATCH_ID),
                new ProductResultSetMapper().resultSetMap(rs, prefix + ProductBatchTable.NAME + "_"),
                rs.getInt(prefix+ProductBatchTable.NAME+"."+ProductBatchTable.Columns.BATCH_NO),
                rs.getDate(prefix+ProductBatchTable.NAME+"."+ProductBatchTable.Columns.EAT_BY_DATE),
                rs.getInt(prefix+ProductBatchTable.NAME+"."+ProductBatchTable.Columns.DISCOUNT),
                rs.getInt(prefix+ ProductBatchTable.NAME+"."+ProductBatchTable.Columns.PACKAGES_QUANTITY));
    }
}
