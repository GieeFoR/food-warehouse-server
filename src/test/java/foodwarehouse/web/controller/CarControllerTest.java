package foodwarehouse.web.controller;

import foodwarehouse.web.request.car.CreateCarRequest;
import foodwarehouse.web.request.car.UpdateCarRequest;
import foodwarehouse.web.response.car.CarResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarControllerTest {

    @Autowired
    private CarController service;

    @Test
    @WithMockUser(username="Admin",roles={"Admin"})
    void getCars_Test() {
        List<CarResponse> result = service.getCars().result();
        assertThat(result.size()).isGreaterThan(0);
    }

    @Test
    @WithMockUser(username="Admin", roles={"Admin"})
    void getCarById_Test() {
        int id = 1;
        CarResponse result = service.getCarById(id).result();
        System.out.println("Szukany pojazd o ID: " + id);
        System.out.println("Pojazd:");
        System.out.println(
                        result.carDataResponse().brand() +
                        " " +
                        result.carDataResponse().model());
        System.out.println("ID: " + result.carDataResponse().carId());
        System.out.println("Kierowca:");
        System.out.println(
                        result.employeeResponse().employeeDataResponse().name() +
                        " " +
                        result.employeeResponse().employeeDataResponse().surname());
        Assert.assertEquals(result.carDataResponse().carId(), id);
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"Admin"})
    void createCar_Test() {
        CreateCarRequest createCarRequest = new CreateCarRequest(
                1,
                "Fiat",
                "Punto",
                1999,
                "KRK 12313",
                new Date(),
                new Date());
        CarResponse carResponse = service
                .createCar(createCarRequest)
                .result();

        Assert.assertEquals(createCarRequest.driverId(), carResponse.employeeResponse().employeeDataResponse().employeeId());
        Assert.assertEquals(createCarRequest.brand(), carResponse.carDataResponse().brand());
        Assert.assertEquals(createCarRequest.model(), carResponse.carDataResponse().model());
        Assert.assertEquals(createCarRequest.yearOfProd(), carResponse.carDataResponse().yearOfProd());
        Assert.assertEquals(createCarRequest.registrationNumber(), carResponse.carDataResponse().registrationNumber());
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"Admin"})
    void updateCar_Test() {
        UpdateCarRequest updateCarRequest = new UpdateCarRequest(
                3,
                1,
                "Fiat",
                "Punto",
                1999,
                "KRK 12313",
                new Date(),
                new Date());
        CarResponse carResponse = service
                .updateCar(updateCarRequest)
                .result();

        Assert.assertEquals(updateCarRequest.carId(), carResponse.carDataResponse().carId());
        Assert.assertEquals(updateCarRequest.driverId(), carResponse.employeeResponse().employeeDataResponse().employeeId());
        Assert.assertEquals(updateCarRequest.brand(), carResponse.carDataResponse().brand());
        Assert.assertEquals(updateCarRequest.model(), carResponse.carDataResponse().model());
        Assert.assertEquals(updateCarRequest.yearOfProd(), carResponse.carDataResponse().yearOfProd());
        Assert.assertEquals(updateCarRequest.registrationNumber(), carResponse.carDataResponse().registrationNumber());
    }
}
