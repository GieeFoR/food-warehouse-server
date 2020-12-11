package foodwarehouse.core.user.student;

import foodwarehouse.core.common.Address;
import foodwarehouse.core.faculty.Group;
import foodwarehouse.core.user.User;

import java.time.LocalDate;
import java.util.Set;

public record Student(
        int studentId,
        User user,
        Address address,
        Set<Group> groups,
        String firstName,
        String lastName,
        int albumNumber,
        LocalDate birthDate,
        long pesel,
        int phoneNumber) {
}
