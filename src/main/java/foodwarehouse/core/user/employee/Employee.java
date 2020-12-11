package foodwarehouse.core.user.employee;

import foodwarehouse.core.common.Address;
import foodwarehouse.core.user.User;

import java.time.LocalDate;
import java.util.Set;

public record Employee(
        int employeeId,
        User user,
        Address address,
        Set<Position> positions,
        Set<Permission> permissions,
        String firstName,
        String lastName,
        LocalDate birthDate,
        long pesel,
        int phoneNumber) {
}
