package universitymanagement.core.event;

import universitymanagement.core.building.Room;
import universitymanagement.core.user.student.Student;
import universitymanagement.core.user.employee.Employee;

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
