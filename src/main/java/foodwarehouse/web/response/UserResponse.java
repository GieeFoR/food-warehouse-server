package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
        @JsonProperty("userId")             int userId,
        @JsonProperty("username")           String username,
        @JsonProperty("email")              String email,
        @JsonProperty("permission")         String permission){
}
