package foodwarehouse.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.user.Account;

public record CreateUserRequest(
        @JsonProperty("account")Account account) {
}
