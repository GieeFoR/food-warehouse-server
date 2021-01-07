package foodwarehouse.core.data.employee;

import foodwarehouse.core.data.user.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary);

    Optional<Employee> updateEmployee(int employeeId, User user, String name, String surname, String position, Float salary);

    boolean deleteEmployee(int employeeId);

    List<Employee> findAllEmployees();

    Optional<Employee> findEmployeeById(int employeeId);
}
