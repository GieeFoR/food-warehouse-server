package foodwarehouse.web.controller.manager;

import foodwarehouse.core.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {

    private final UserService userService;

    public ManagerController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasRole('Manager')")
}
