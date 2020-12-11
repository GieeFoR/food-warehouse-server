package universitymanagement.core.user;

import java.util.Optional;

public enum UserType {
    ADMIN("Admin"),
    EMPLOYEE("Employee"),
    STUDENT("Student");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public static Optional<UserType> from(String value) {
        return switch (value) {
            case "Admin" -> Optional.of(UserType.ADMIN);
            case "Employee" -> Optional.of(UserType.EMPLOYEE);
            case "Student" -> Optional.of(UserType.STUDENT);
            default -> Optional.empty();
        };
    }

    public String value() {
        return value;
    }
}
