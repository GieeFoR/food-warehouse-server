package foodwarehouse.core.database.rowmappers;

import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper<T> {
    @Nullable
    T resultSetMap(ResultSet rs) throws SQLException;
}
