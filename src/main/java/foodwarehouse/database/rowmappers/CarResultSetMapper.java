package foodwarehouse.database.rowmappers;

import foodwarehouse.core.data.car.Car;
import foodwarehouse.database.tables.CarTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarResultSetMapper implements ResultSetMapper<Car> {
    @Override
    public Car resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Car(
                rs.getInt(prefix+CarTable.NAME+"."+CarTable.Columns.CAR_ID),
                new EmployeeResultSetMapper().resultSetMap(rs, prefix + CarTable.NAME + "_"),
                rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.BRAND),
                rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.MODEL),
                rs.getInt(prefix+CarTable.NAME+"."+CarTable.Columns.PROD_YEAR),
                rs.getString(prefix+CarTable.NAME+"."+CarTable.Columns.REG_NO),
                rs.getDate(prefix+CarTable.NAME+"."+CarTable.Columns.INSURANCE),
                rs.getDate(prefix+CarTable.NAME+"."+CarTable.Columns.INSPECTION));
    }
}
