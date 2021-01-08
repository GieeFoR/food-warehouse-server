package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.user.User;

public record UserResponse(
        @JsonProperty(value = "user_id", required = true)            int userId,
        @JsonProperty(value = "username", required = true)           String username,
        @JsonProperty(value = "email", required = true)              String email,
        @JsonProperty(value = "permission", required = true)         String permission){

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.userId(),
                user.username(),
                user.email(),
                user.permission().value());
    }
}
