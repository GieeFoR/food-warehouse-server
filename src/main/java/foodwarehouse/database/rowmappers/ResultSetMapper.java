package foodwarehouse.database.rowmappers;

import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public interface ResultSetMapper<T> {
    @Nullable
    T resultSetMap(ResultSet rs, String prefix) throws SQLException, ParseException;
}
