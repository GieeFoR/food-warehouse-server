package foodwarehouse.web.response.others;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckEmailResponse(
        @JsonProperty(value = "emailExists", required = true) boolean emailExists) {
}
