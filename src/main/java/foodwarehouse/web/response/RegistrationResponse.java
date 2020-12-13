package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegistrationResponse(
        @JsonProperty("created") boolean created) {

}
