package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckEmailResponse(
        @JsonProperty("emailExists") boolean emailExists) {
}
