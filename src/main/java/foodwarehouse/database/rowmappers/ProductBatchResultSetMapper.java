package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.database.tables.ComplaintTable;
import foodwarehouse.database.tables.OrderTable;
import foodwarehouse.database.tables.ProductBatchTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

final public class ProductBatchResultSetMapper implements ResultSetMapper<ProductBatch> {
    @Override
    public ProductBatch resultSetMap(ResultSet rs, String prefix) throws SQLException, ParseException {
        return new ProductBatch(
                rs.getInt(prefix+ProductBatchTable.NAME+"."+ProductBatchTable.Columns.BATCH_ID),
                new ProductResultSetMapper().resultSetMap(rs, prefix + ProductBatchTable.NAME + "_"),
                rs.getInt(prefix+ProductBatchTable.NAME+"."+ProductBatchTable.Columns.BATCH_NO),
                rs.getString(prefix+ProductBatchTable.NAME+"."+ProductBatchTable.Columns.EAT_BY_DATE) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(prefix+ProductBatchTable.NAME+"."+ProductBatchTable.Columns.EAT_BY_DATE)),
                rs.getInt(prefix+ProductBatchTable.NAME+"."+ProductBatchTable.Columns.DISCOUNT),
                rs.getInt(prefix+ ProductBatchTable.NAME+"."+ProductBatchTable.Columns.PACKAGES_QUANTITY));
    }
}
