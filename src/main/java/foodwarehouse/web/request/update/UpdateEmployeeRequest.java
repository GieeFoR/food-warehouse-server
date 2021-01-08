package foodwarehouse.web.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateEmployeeRequest(
        @JsonProperty(value = "account", required = true)        UpdateUserRequest updateUserRequest,
        @JsonProperty(value = "personal_data", required = true)  UpdateEmployeeData updateEmployeeData) {
}
