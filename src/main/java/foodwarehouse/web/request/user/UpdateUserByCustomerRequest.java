package foodwarehouse.web.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserByCustomerRequest(
        @JsonProperty(value = "username", required = true)       String username,
        @JsonProperty(value = "password", required = true)       String password,
        @JsonProperty(value = "email", required = true)          String email) {
}
