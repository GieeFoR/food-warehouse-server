package foodwarehouse.web.request.update.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.update.UpdateUserRequest;

public record UpdateEmployeeRequest(
        @JsonProperty(value = "account", required = true)UpdateUserRequest updateUserRequest,
        @JsonProperty(value = "personal_data", required = true)  UpdateEmployeeData updateEmployeeData) {
}
