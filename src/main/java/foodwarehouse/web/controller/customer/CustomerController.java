package foodwarehouse.web.controller.customer;

import foodwarehouse.core.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final UserService userService;

    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasRole('Customer')")
}
