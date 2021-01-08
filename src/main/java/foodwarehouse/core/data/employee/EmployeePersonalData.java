package foodwarehouse.core.data.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeePersonalData(
        @JsonProperty("name")           String name,
        @JsonProperty("surname")        String surname,
        @JsonProperty("position")       String position,
        @JsonProperty("salary")         Float salary) {

    public static EmployeePersonalData fromEmployee(Employee employee) {
        return new EmployeePersonalData(
                employee.name(),
                employee.surname(),
                employee.position(),
                employee.salary());
    }
}
