package foodwarehouse.web.controller;

import foodwarehouse.web.request.employee.CreateEmployeeData;
import foodwarehouse.web.request.employee.CreateEmployeeRequest;
import foodwarehouse.web.request.employee.UpdateEmployeeData;
import foodwarehouse.web.request.employee.UpdateEmployeeRequest;
import foodwarehouse.web.request.user.CreateUserRequest;
import foodwarehouse.web.request.user.UpdateUserRequest;
import foodwarehouse.web.response.employee.EmployeeResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeController service;

    @Test
    @WithMockUser(username="Customer", roles={"Customer"})
    void getEmployees_Test_WrongPermission() throws Exception {
        mvc.perform(get("/employee")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="Admin", roles={"Admin"})
    void getEmployees_Test() {
        Assert.assertEquals(5 ,service.getEmployees().result().size());
    }

    @Test
    @WithMockUser(username="Admin", roles={"Admin"})
    void getEmployeeById_Test() {
        int id = 1;
        Assert.assertEquals(id ,service.getEmployeeById(id).result().employeeDataResponse().employeeId());
    }

    @Test
    @WithMockUser(username="Admin", roles={"Admin"})
    void createEmployee_Test() {
        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest(
                new CreateUserRequest(
                        "test",
                        "test",
                        "test@example.com",
                        "Customer"),
                new CreateEmployeeData(
                        "test",
                        "test",
                        "test",
                        111.11f));

        EmployeeResponse employeeResponse = service.createEmployee(createEmployeeRequest).result();

        Assert.assertEquals(createEmployeeRequest.createEmployeeData().name(), employeeResponse.employeeDataResponse().name());
        Assert.assertEquals(createEmployeeRequest.createEmployeeData().surname(), employeeResponse.employeeDataResponse().surname());
        Assert.assertEquals(createEmployeeRequest.createUserRequest().username(), employeeResponse.userResponse().username());
        Assert.assertEquals(createEmployeeRequest.createUserRequest().email(), employeeResponse.userResponse().email());
    }

    @Test
    @WithMockUser(username="Admin", roles={"Admin"})
    void updateEmployee_Test() {
        UpdateEmployeeRequest updateEmployeeRequest = new UpdateEmployeeRequest(
                new UpdateUserRequest(
                        18,
                        "test2",
                        "test2",
                        "test2@example.com",
                        "Customer"),
                new UpdateEmployeeData(
                        9,
                        "test2",
                        "test2",
                        "test2",
                        11111.11f));

        EmployeeResponse employeeResponse = service.updateEmployee(updateEmployeeRequest).result();

        Assert.assertEquals(updateEmployeeRequest.updateEmployeeData().employeeId(), employeeResponse.employeeDataResponse().employeeId());
        Assert.assertEquals(updateEmployeeRequest.updateEmployeeData().name(), employeeResponse.employeeDataResponse().name());
        Assert.assertEquals(updateEmployeeRequest.updateEmployeeData().surname(), employeeResponse.employeeDataResponse().surname());
        Assert.assertEquals(updateEmployeeRequest.updateUserRequest().userId(), employeeResponse.userResponse().userId());
        Assert.assertEquals(updateEmployeeRequest.updateUserRequest().username(), employeeResponse.userResponse().username());
        Assert.assertEquals(updateEmployeeRequest.updateUserRequest().email(), employeeResponse.userResponse().email());
    }

    @Test
    @WithMockUser(username="Admin", roles={"Admin"})
    void deleteEmployeeById_Test() {
        Assert.assertTrue(service.deleteEmployeeById(9).result().deleted());
    }
}