package foodwarehouse.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckEmailRequest(
        @JsonProperty("email") String email) {
}
