package foodwarehouse.core.user.employee;

import foodwarehouse.core.user.User;

public record Employee(
        int employeeId,
        User user,
        String name,
        String surname,
        String position,
        float salary) {
}
