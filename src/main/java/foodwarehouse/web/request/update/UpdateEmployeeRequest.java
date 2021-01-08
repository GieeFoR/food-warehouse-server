package foodwarehouse.web.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.EmployeePersonalData;

public record UpdateEmployeeRequest(
        @JsonProperty(value = "user_id", required = true)        int userId,
        @JsonProperty(value = "employee_id", required = true)    int employeeId,
        @JsonProperty(value = "account", required = true)        UpdateUserRequest updateUserRequest,
        @JsonProperty(value = "personal_data", required = true)  EmployeePersonalData employeePersonalData) {
}
