package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.car.CarInfo;

public record CarResponse (
        @JsonProperty(value = "car_info", required = true)       CarInfo carInfo,
        @JsonProperty(value = "driver", required = true)         EmployeeResponse employeeResponse){

    public static CarResponse fromCar(Car car) {
        return new CarResponse(
                CarInfo.fromCar(car),
                EmployeeResponse.fromEmployee(car.driver())
        );
    }
}
