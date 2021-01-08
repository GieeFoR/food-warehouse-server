package foodwarehouse.web.response.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.car.Car;

import java.util.Date;

public record CarDataResponse(
        @JsonProperty(value = "car_id", required = true)        int carId,
        @JsonProperty(value = "brand", required = true)         String brand,
        @JsonProperty(value = "model", required = true)         String model,
        @JsonProperty(value = "prod_year", required = true)     int yearOfProd,
        @JsonProperty(value = "reg_no", required = true)        String registrationNumber,
        @JsonProperty(value = "insurance", required = true)     Date insuranceExp,
        @JsonProperty(value = "inspection", required = true)    Date inspectionExp
) {

    public static CarDataResponse fromCar(Car car) {
        return new CarDataResponse(
                car.carId(),
                car.brand(),
                car.model(),
                car.yearOfProd(),
                car.registrationNumber(),
                car.insuranceExp(),
                car.inspectionExp());
    }
}
