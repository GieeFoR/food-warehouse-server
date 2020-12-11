package foodwarehouse.core.event;

import foodwarehouse.core.building.Room;
import foodwarehouse.core.user.student.Student;
import foodwarehouse.core.user.employee.Employee;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public record Event(
        int eventId,
        Employee owner,
        Room room,
        Set<Employee> employees,
        Set<Student> students,
        String name,
        String description,
        LocalDate date,
        LocalTime timeFrom,
        LocalTime timeTo) {
}
