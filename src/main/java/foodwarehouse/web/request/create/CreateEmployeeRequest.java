package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateEmployeeRequest(
        @JsonProperty(value = "account", required = true)            CreateUserRequest createUserRequest,
        @JsonProperty(value = "personal_data", required = true)      CreateEmployeeData createEmployeeData) {
}
