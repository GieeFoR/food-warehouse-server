package foodwarehouse.core.grade;

import foodwarehouse.core.user.student.Student;
import foodwarehouse.core.user.employee.Employee;

public record Grade(String gradeId, Employee employee, Student student, String name, String description, float value) {
}
