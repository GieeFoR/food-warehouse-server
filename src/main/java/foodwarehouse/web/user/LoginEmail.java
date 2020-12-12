package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginEmail(@JsonProperty("email") String email) {
}
