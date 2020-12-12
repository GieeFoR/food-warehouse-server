package foodwarehouse.web.user;

public record CreateUserRequest(String username, String password, String email, String permission) {
}
