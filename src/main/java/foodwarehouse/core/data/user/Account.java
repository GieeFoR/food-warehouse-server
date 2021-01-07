package foodwarehouse.core.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Account(
        @JsonProperty("user_id")        int userId,
        @JsonProperty("username")       String username,
        @JsonProperty("password")       String password,
        @JsonProperty("email")          String email,
        @JsonProperty("permission")     String permission) {

    public static Account fromUser(User user) {
        return new Account(
                user.userId(),
                user.username(),
                "",
                user.email(),
                user.permission().value());
    }
}
