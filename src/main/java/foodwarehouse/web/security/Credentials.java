package foodwarehouse.web.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Credentials(@JsonProperty("username") String username, @JsonProperty("password") String password) {
}
