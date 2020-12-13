package foodwarehouse.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckUsernameRequest(@JsonProperty("username") String username) {
}
