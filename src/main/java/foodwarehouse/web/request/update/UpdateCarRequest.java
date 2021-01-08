package foodwarehouse.web.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.car.Car;

import java.util.Date;

public record UpdateCarRequest(
        @JsonProperty("car_id")         int carId,
        @JsonProperty("driver_id")      int driverId,
        @JsonProperty("brand")          String brand,
        @JsonProperty("model")          String model,
        @JsonProperty("prod_year")      int yearOfProd,
        @JsonProperty("reg_no")         String registrationNumber,
        @JsonProperty("insurance")      Date insuranceExp,
        @JsonProperty("inspection")     Date inspectionExp) {

    public static UpdateCarRequest fromCar(Car car) {
        return new UpdateCarRequest(
                car.carId(),
                car.driver().employeeId(),
                car.brand(),
                car.model(),
                car.yearOfProd(),
                car.registrationNumber(),
                car.insuranceExp(),
                car.inspectionExp());
    }
}
