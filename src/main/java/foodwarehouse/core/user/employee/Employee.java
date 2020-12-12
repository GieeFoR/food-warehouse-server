package foodwarehouse.core.user.employee;

import foodwarehouse.core.user.User;
import org.apache.tomcat.jni.Address;

import java.time.LocalDate;
import java.util.Set;

public record Employee(
        int employeeId,
        User user,
        Set<Position> positions,
        Set<Permission> permissions,
        Address address,
        String firstName,
        String lastName,
        LocalDate birthDate,
        long pesel,
        int phoneNumber) {
}
