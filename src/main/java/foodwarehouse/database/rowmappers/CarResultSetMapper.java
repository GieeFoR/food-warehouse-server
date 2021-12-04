package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.car.Car;
import foodwarehouse.database.tables.CarTable;
import foodwarehouse.database.tables.ComplaintTable;
import foodwarehouse.database.tables.OrderTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

final public class CarResultSetMapper implements ResultSetMapper<Car> {
    @Override
    public Car resultSetMap(ResultSet rs, String prefix) throws SQLException, ParseException {
        return new Car(
                rs.getInt(prefix+CarTable.NAME+"."+CarTable.Columns.CAR_ID),
                new EmployeeResultSetMapper().resultSetMap(rs, prefix + CarTable.NAME + "_"),
                rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.BRAND),
                rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.MODEL),
                rs.getInt(prefix+CarTable.NAME+"."+CarTable.Columns.PROD_YEAR),
                rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.REG_NO),
                rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.INSURANCE) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.INSURANCE)),
                rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.INSPECTION) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.INSPECTION)));
    }
}
