package foodwarehouse.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.user.Account;

public record CreateUserRequest(
        @JsonProperty("account")Account account) {
}
