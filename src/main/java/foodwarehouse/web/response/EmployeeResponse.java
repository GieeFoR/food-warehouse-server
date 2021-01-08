package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeePersonalData;

public record EmployeeResponse (
        @JsonProperty("user_id")            int userId,
        @JsonProperty("employee_id")        int employee_id,
        @JsonProperty("account")            UserResponse userResponse,
        @JsonProperty("personal_data")      EmployeePersonalData employeePersonalData) {

    public static EmployeeResponse fromEmployee(Employee employee) {
        return new EmployeeResponse(
                employee.user().userId(),
                employee.employeeId(),
                UserResponse.fromUser(employee.user()),
                EmployeePersonalData.fromEmployee(employee));
    }
}
