package foodwarehouse.core.data.user;

import foodwarehouse.web.response.UserResponse;

public record User (
        int userId,
        String username,
        String password,
        String email,
        Permission permission) {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.userId(),
                user.username(),
                user.email(),
                user.permission().value());
    }
}
