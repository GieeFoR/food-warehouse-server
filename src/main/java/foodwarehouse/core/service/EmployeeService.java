package foodwarehouse.core.service;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeeRepository;
import foodwarehouse.core.data.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements EmployeeRepository {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary) throws SQLException {
        return employeeRepository.createEmployee(user, name, surname, position, salary);
    }

    @Override
    public Optional<Employee> updateEmployee(int employeeId, User user, String name, String surname, String position, Float salary) {
        return employeeRepository.updateEmployee(employeeId, user, name, surname, position, salary);
    }

    @Override
    public List<Employee> findAllEmployees() throws SQLException {
        return employeeRepository.findAllEmployees();
    }
}
