package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginUsername(@JsonProperty("username") String username) {
}
