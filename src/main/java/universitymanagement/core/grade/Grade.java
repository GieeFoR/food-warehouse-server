package universitymanagement.core.grade;

import universitymanagement.core.user.student.Student;
import universitymanagement.core.user.employee.Employee;

public record Grade(String gradeId, Employee employee, Student student, String name, String description, float value) {
}
