package foodwarehouse.web.response.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.car.Car;
import foodwarehouse.web.response.employee.EmployeeResponse;

public record CarResponse (
        @JsonProperty(value = "car_info", required = true)       CarDataResponse carDataResponse,
        @JsonProperty(value = "driver", required = true)         EmployeeResponse employeeResponse){

    public static CarResponse fromCar(Car car) {
        return new CarResponse(
                CarDataResponse.fromCar(car),
                EmployeeResponse.fromEmployee(car.driver())
        );
    }
}
