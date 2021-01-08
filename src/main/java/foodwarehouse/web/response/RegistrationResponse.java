package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegistrationResponse(
        @JsonProperty(value = "created", required = true) boolean created) {

}
