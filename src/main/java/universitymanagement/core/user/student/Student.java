package universitymanagement.core.user.student;

import universitymanagement.core.common.Address;
import universitymanagement.core.faculty.Group;
import universitymanagement.core.user.User;

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
