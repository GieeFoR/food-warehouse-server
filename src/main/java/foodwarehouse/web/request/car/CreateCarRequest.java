package foodwarehouse.web.request.car;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record CreateCarRequest (
        @JsonProperty(value = "driver_id", required = true)      int driverId,
        @JsonProperty(value = "brand", required = true)          String brand,
        @JsonProperty(value = "model", required = true)          String model,
        @JsonProperty(value = "prod_year", required = true)      int yearOfProd,
        @JsonProperty(value = "reg_no", required = true)         String registrationNumber,
        @JsonProperty(value = "insurance", required = true)      Date insuranceExp,
        @JsonProperty(value = "inspection", required = true)     Date inspectionExp) {
}
