package universitymanagement.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
        @JsonProperty("userType") String userType,
        @JsonProperty("userId") int userId,
        @JsonProperty("email") String email) {
}
