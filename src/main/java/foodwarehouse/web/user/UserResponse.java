package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
        @JsonProperty("permission") String permission,
        @JsonProperty("userId") int userId,
        @JsonProperty("username") String username) {
}
