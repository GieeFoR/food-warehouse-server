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
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> createEmployee(User user, String name, String surname, String position, Float salary) {
        return employeeRepository.createEmployee(user, name, surname, position, salary);
    }

    public Optional<Employee> updateEmployee(int employeeId, User user, String name, String surname, String position, Float salary) {
        return employeeRepository.updateEmployee(employeeId, user, name, surname, position, salary);
    }

    public boolean deleteEmployee(int employeeId) {
        return employeeRepository.deleteEmployee(employeeId);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    public Optional<Employee> findEmployeeById(int employeeId) {
        return employeeRepository.findEmployeeById(employeeId);
    }

    public Optional<Employee> findSupplierWithMinDelivery() {
        return employeeRepository.findSupplierWithMinDelivery();
    }
}
