package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserRequest(
        @JsonProperty(value = "username", required = true)       String username,
        @JsonProperty(value = "password", required = true)       String password,
        @JsonProperty(value = "email", required = true)          String email,
        @JsonProperty(value = "permission", required = true)     String permission) {
}
