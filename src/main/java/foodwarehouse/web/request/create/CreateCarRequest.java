package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.car.CarInfo;

public record CreateCarRequest(
        @JsonProperty(value = "employee_id", required = true)   int employeeId,
        @JsonProperty(value = "car", required = true)           CarInfo carInfo) {
}
