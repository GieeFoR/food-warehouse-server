package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.product.Product;
import foodwarehouse.database.tables.ProductTable;

import java.sql.ResultSet;
import java.sql.SQLException;

final public class ProductResultSetMapper implements ResultSetMapper<Product> {
    @Override
    public Product resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Product(
                rs.getInt(ProductTable.Columns.PRODUCT_ID),
                new MakerResultSetMapper().resultSetMap(rs, prefix + ProductTable.NAME + "_"),
                rs.getString(ProductTable.Columns.PRODUCT_NAME),
                rs.getString(ProductTable.Columns.SHORT_DESC),
                rs.getString(ProductTable.Columns.LONG_DESC),
                rs.getString(ProductTable.Columns.CATEGORY),
                rs.getBoolean(ProductTable.Columns.NEED_COLD),
                rs.getFloat(ProductTable.Columns.BUY_PRICE),
                rs.getFloat(ProductTable.Columns.SELL_PRICE),
                rs.getBlob(ProductTable.Columns.IMAGE));
    }
}
