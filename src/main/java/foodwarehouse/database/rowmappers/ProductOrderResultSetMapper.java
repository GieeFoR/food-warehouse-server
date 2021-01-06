package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.productOrder.ProductOrder;
import foodwarehouse.database.tables.ProductOrderTable;

import java.sql.ResultSet;
import java.sql.SQLException;

final public class ProductOrderResultSetMapper implements ResultSetMapper<ProductOrder> {
    @Override
    public ProductOrder resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new ProductOrder(
                new OrderResultSetMapper().resultSetMap(rs, prefix + ProductOrderTable.NAME + "_"),
                new ProductBatchResultSetMapper().resultSetMap(rs, prefix + ProductOrderTable.NAME + "_"),
                rs.getInt(prefix+ProductOrderTable.NAME+"."+ProductOrderTable.Columns.QUANTITY));
    }
}
