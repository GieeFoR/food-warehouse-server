package foodwarehouse.web.controller.employee;

import foodwarehouse.core.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final UserService userService;

    public EmployeeController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasRole('Employee')")
}
