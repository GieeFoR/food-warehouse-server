package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.Employee;

public record CreateEmployeeData(
        @JsonProperty(value = "name", required = true)           String name,
        @JsonProperty(value = "surname", required = true)        String surname,
        @JsonProperty(value = "position", required = true)       String position,
        @JsonProperty(value = "salary", required = true)         Float salary) {

    public static CreateEmployeeData fromEmployee(Employee employee) {
        return new CreateEmployeeData(
                employee.name(),
                employee.surname(),
                employee.position(),
                employee.salary());
    }
}
