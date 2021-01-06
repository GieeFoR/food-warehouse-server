package foodwarehouse.database.rowmappers;

import foodwarehouse.database.tables.EmployeeTable;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.database.tables.OrderTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeResultSetMapper implements ResultSetMapper<Employee> {
    @Override
    public Employee resultSetMap(ResultSet rs, String prefix) throws SQLException {
        return new Employee(
                rs.getInt(prefix+EmployeeTable.NAME+"."+EmployeeTable.Columns.EMPLOYEE_ID),
                new UserResultSetMapper().resultSetMap(rs, prefix + EmployeeTable.NAME + "_"),
                rs.getString(prefix+EmployeeTable.NAME+"."+EmployeeTable.Columns.NAME),
                rs.getString(prefix+EmployeeTable.NAME+"."+EmployeeTable.Columns.SURNAME),
                rs.getString(prefix+EmployeeTable.NAME+"."+EmployeeTable.Columns.POSITION),
                rs.getFloat(prefix+EmployeeTable.NAME+"."+EmployeeTable.Columns.SALARY));
    }
}
