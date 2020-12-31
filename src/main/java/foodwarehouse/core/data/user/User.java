package foodwarehouse.core.data.user;

public record User (
        int userId,
        String username,
        String password,
        String email,
        Permission permission) {
}
