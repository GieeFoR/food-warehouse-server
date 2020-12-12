package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsernameResponse(@JsonProperty("usernameExists") boolean usernameExists) {
}
