package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.employee.EmployeePersonalData;

public record EmployeeResponse (
        @JsonProperty("account")            UserResponse userResponse,
        @JsonProperty("personal_data")      EmployeePersonalData employeePersonalData) {

    public static EmployeeResponse fromEmployee(Employee employee) {
        return new EmployeeResponse(
            UserResponse.fromUser(employee.user()),
                EmployeePersonalData.fromEmployee(employee));
    }
}
