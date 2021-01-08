package foodwarehouse.web.request.others;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckEmailRequest(
        @JsonProperty(value = "email", required = true) String email) {
}
