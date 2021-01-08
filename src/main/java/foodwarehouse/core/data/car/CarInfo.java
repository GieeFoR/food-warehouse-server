package foodwarehouse.core.data.car;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record CarInfo(
        @JsonProperty("brand")          String brand,
        @JsonProperty("model")          String model,
        @JsonProperty("year_of_prod")   int yearOfProd,
        @JsonProperty("reg_no")         String registrationNumber,
        @JsonProperty("insurance")      Date insuranceExp,
        @JsonProperty("inspection")     Date inspectionExp) {

    public static CarInfo fromCar(Car car) {
        return new CarInfo(
                car.brand(),
                car.model(),
                car.yearOfProd(),
                car.registrationNumber(),
                car.insuranceExp(),
                car.inspectionExp());
    }
}
