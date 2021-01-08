package foodwarehouse.web.request.update.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.Employee;

public record UpdateEmployeeData(
        @JsonProperty(value = "employee_id", required = true)    int employeeId,
        @JsonProperty(value = "name", required = true)           String name,
        @JsonProperty(value = "surname", required = true)        String surname,
        @JsonProperty(value = "position", required = true)       String position,
        @JsonProperty(value = "salary", required = true)         Float salary) {

    public static UpdateEmployeeData fromEmployee(Employee employee) {
        return new UpdateEmployeeData(
                employee.employeeId(),
                employee.name(),
                employee.surname(),
                employee.position(),
                employee.salary());
    }
}
