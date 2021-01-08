package foodwarehouse.web.request.others;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckUsernameRequest(
        @JsonProperty(value = "username", required = true) String username) {
}
