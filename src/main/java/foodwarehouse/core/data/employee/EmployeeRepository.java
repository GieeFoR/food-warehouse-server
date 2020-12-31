package foodwarehouse.core.data.employee;

import foodwarehouse.core.data.user.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary) throws SQLException;

    Optional<Employee> updateEmployee(int employeeId, User user, String name, String surname, String position, Float salary);

    List<Employee> findAllEmployees() throws SQLException;


}
