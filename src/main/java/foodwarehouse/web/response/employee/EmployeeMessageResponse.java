package foodwarehouse.web.response.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.Employee;

public record EmployeeMessageResponse(
        @JsonProperty(value = "employee_id", required = true)    int employeeId,
        @JsonProperty(value = "name", required = true)           String name,
        @JsonProperty(value = "surname", required = true)        String surname) {

    public static EmployeeMessageResponse fromEmployee(Employee employee) {
        return new EmployeeMessageResponse(
                employee.employeeId(),
                employee.name(),
                employee.surname());
    }
}
