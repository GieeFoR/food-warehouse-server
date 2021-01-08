package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.employee.EmployeePersonalData;

public record CreateEmployeeRequest(
        @JsonProperty(value = "account", required = true)            CreateUserRequest createUserRequest,
        @JsonProperty(value = "personal_data", required = true)      EmployeePersonalData employeePersonalData) {
}
