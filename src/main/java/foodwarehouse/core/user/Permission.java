package foodwarehouse.core.user;

import java.util.Optional;

public enum Permission {
    ADMIN("Admin"),
    MANAGER("Manager"),
    EMPLOYEE("Employee"),
    SUPPLIER("Supplier"),
    CUSTOMER("Customer");

    private final String value;

    Permission(String value) {
        this.value = value;
    }

    public static Optional<Permission> from(String value) {
        return switch (value) {
            case "Admin" -> Optional.of(Permission.ADMIN);
            case "Manager" -> Optional.of(Permission.MANAGER);
            case "Employee" -> Optional.of(Permission.EMPLOYEE);
            case "Supplier" -> Optional.of(Permission.SUPPLIER);
            case "Customer" -> Optional.of(Permission.CUSTOMER);
            default -> Optional.empty();
        };
    }

    public String value() {
        return value;
    }
}
