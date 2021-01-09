package foodwarehouse.web.response.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.web.response.user.UserResponse;

public record EmployeeResponse (
        @JsonProperty(value = "account", required = true)            UserResponse userResponse,
        @JsonProperty(value = "personal_data", required = true)      EmployeeDataResponse employeeDataResponse) {

    public static EmployeeResponse fromEmployee(Employee employee) {
        return new EmployeeResponse(
                UserResponse.fromUser(employee.user()),
                EmployeeDataResponse.fromEmployee(employee));
    }
}
