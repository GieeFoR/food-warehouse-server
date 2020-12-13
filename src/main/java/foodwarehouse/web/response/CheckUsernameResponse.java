package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckUsernameResponse(
        @JsonProperty("usernameExists") boolean usernameExists) {
}
