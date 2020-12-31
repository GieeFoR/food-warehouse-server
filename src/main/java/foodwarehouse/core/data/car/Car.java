package foodwarehouse.core.data.car;

import foodwarehouse.core.data.employee.Employee;

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
