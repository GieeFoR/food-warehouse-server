package foodwarehouse.web.request.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.user.CreateUserRequest;

public record CreateEmployeeRequest(
        @JsonProperty(value = "account", required = true)CreateUserRequest createUserRequest,
        @JsonProperty(value = "personal_data", required = true)      CreateEmployeeData createEmployeeData) {
}
