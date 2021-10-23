package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.product.Product;
import foodwarehouse.database.tables.ProductTable;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

final public class ProductResultSetMapper implements ResultSetMapper<Product> {
    @Override
    public Product resultSetMap(ResultSet rs, String prefix) throws SQLException {
        Blob blob = rs.getBlob(ProductTable.Columns.IMAGE);

        return new Product(
                rs.getInt(prefix+ProductTable.NAME+"."+ProductTable.Columns.PRODUCT_ID),
                new MakerResultSetMapper().resultSetMap(rs, prefix + ProductTable.NAME + "_"),
                rs.getString(prefix+ProductTable.NAME+"."+ProductTable.Columns.PRODUCT_NAME),
                rs.getString(prefix+ProductTable.NAME+"."+ProductTable.Columns.SHORT_DESC),
                rs.getString(prefix+ProductTable.NAME+"."+ProductTable.Columns.LONG_DESC),
                rs.getString(prefix+ProductTable.NAME+"."+ProductTable.Columns.CATEGORY),
                rs.getString(prefix+ProductTable.NAME+"."+ProductTable.Columns.NEED_COLD).equals("Y"),
                rs.getFloat(prefix+ProductTable.NAME+"."+ProductTable.Columns.BUY_PRICE),
                rs.getFloat(prefix+ProductTable.NAME+"."+ProductTable.Columns.SELL_PRICE),
                new String(blob.getBytes(1, (int) blob.length())));
                //new String(blob.toBy);
    }
}
