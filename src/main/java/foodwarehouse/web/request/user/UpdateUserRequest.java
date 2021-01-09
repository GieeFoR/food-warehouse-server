package foodwarehouse.web.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserRequest(
        @JsonProperty(value = "user_id", required = true)        int userId,
        @JsonProperty(value = "username", required = true)       String username,
        @JsonProperty(value = "password", required = true)       String password,
        @JsonProperty(value = "email", required = true)          String email,
        @JsonProperty(value = "permission", required = true)     String permission) {
}
