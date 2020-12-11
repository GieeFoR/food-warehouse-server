package foodwarehouse.core.reservation;

import foodwarehouse.core.user.employee.Employee;
import foodwarehouse.core.building.Room;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
        int reservationId,
        Employee owner,
        Room room,
        LocalDate date,
        LocalTime timeFrom,
        LocalTime timeTo) {
}
