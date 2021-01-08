package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.user.User;

public record UserResponse(
        @JsonProperty("username")           String username,
        @JsonProperty("email")              String email,
        @JsonProperty("permission")         String permission){

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.username(),
                user.email(),
                user.permission().value());
    }
}
