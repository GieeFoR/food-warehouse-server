package foodwarehouse.core.data.employee;

import foodwarehouse.core.data.user.User;

public record Employee(
        int employeeId,
        User user,
        String name,
        String surname,
        String position,
        float salary) {
}
