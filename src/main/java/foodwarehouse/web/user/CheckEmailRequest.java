package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckEmailRequest(
        @JsonProperty("email") String email) {
}
