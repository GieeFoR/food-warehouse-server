package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
        @JsonProperty("permission") String permission,
        @JsonProperty("userId") int userId,
        @JsonProperty("username") String username) {
}
