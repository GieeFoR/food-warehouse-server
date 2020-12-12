package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Account(
        @JsonProperty("username")   String username,
        @JsonProperty("password")   String password,
        @JsonProperty("email")      String email,
        @JsonProperty("permission") String permission) {
}
