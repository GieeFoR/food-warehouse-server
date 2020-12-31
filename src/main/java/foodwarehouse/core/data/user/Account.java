package foodwarehouse.core.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Account(
        @JsonProperty("id")         int userId,
        @JsonProperty("username")   String username,
        @JsonProperty("password")   String password,
        @JsonProperty("email")      String email,
        @JsonProperty("permission") String permission) {
}
