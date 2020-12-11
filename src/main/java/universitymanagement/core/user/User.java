package universitymanagement.core.user;

public record User (int userId, UserType userType, String email, String password) {
}
