package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckUsernameResponse(
        @JsonProperty(value = "usernameExists", required = true) boolean usernameExists) {
}
