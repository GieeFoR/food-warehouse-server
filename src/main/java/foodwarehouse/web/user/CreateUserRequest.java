package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserRequest(
        @JsonProperty("account") Account account) {
}
