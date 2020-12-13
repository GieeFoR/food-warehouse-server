package foodwarehouse.web.controller.employee;

import foodwarehouse.core.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final UserService userService;

    public EmployeeController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasRole('Employee')")
}
