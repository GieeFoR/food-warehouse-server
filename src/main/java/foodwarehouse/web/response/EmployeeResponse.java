package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.user.employee.EmployeePersonalData;

public record EmployeeResponse (
        @JsonProperty("account") UserResponse userResponse,
        @JsonProperty("personal_data") EmployeePersonalData employeePersonalData) {
}
