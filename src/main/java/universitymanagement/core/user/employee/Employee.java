package universitymanagement.core.user.employee;

import universitymanagement.core.common.Address;
import universitymanagement.core.user.User;

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
