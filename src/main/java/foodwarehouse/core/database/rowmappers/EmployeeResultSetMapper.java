package foodwarehouse.core.database.rowmappers;

import foodwarehouse.core.database.tables.EmployeeTable;
import foodwarehouse.core.user.employee.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeResultSetMapper implements ResultSetMapper<Employee> {
    @Override
    public Employee resultSetMap(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt(EmployeeTable.Columns.EMPLOYEE_ID),
                new UserResultSetMapper().resultSetMap(rs),
                rs.getString(EmployeeTable.Columns.NAME),
                rs.getString(EmployeeTable.Columns.SURNAME),
                rs.getString(EmployeeTable.Columns.POSITION),
                rs.getFloat(EmployeeTable.Columns.SALARY));
    }
}
