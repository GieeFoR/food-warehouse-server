package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.database.tables.MakerTable;

import java.sql.ResultSet;
import java.sql.SQLException;

final public class MakerResultSetMapper implements ResultSetMapper<Maker> {
    @Override
    public Maker resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Maker(
                rs.getInt(prefix+MakerTable.NAME+"."+MakerTable.Columns.MAKER_ID),
                new AddressResultSetMapper().resultSetMap(rs, prefix+MakerTable.NAME+"_"),
                rs.getString(prefix+MakerTable.NAME+"."+MakerTable.Columns.FIRM_NAME),
                rs.getString(prefix+MakerTable.NAME+"."+MakerTable.Columns.TELEPHONE_NO),
                rs.getString(prefix+MakerTable.NAME+"."+MakerTable.Columns.E_MAIL));
    }
}
