package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.Employee;

public record EmployeeDataResponse(
        @JsonProperty(value = "employee_id", required = true)    int employeeId,
        @JsonProperty(value = "name", required = true)           String name,
        @JsonProperty(value = "surname", required = true)        String surname,
        @JsonProperty(value = "position", required = true)       String position,
        @JsonProperty(value = "salary", required = true)         Float salary) {

    public static EmployeeDataResponse fromEmployee(Employee employee) {
        return new EmployeeDataResponse(
                employee.employeeId(),
                employee.name(),
                employee.surname(),
                employee.position(),
                employee.salary());
    }
}
