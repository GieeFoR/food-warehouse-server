package foodwarehouse.core.car;

import foodwarehouse.core.user.employee.Employee;

import java.util.Date;

public record Car(
        int carId,
        Employee driverId,
        String brand,
        String model,
        int yearOfProd,
        String registrationNumber,
        Date insuranceExp,
        Date inspectionExp) {
}
