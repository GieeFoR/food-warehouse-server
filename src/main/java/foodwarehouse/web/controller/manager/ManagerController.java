package foodwarehouse.web.controller.manager;

import foodwarehouse.core.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ManagerController {

    private final UserService userService;

    public ManagerController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasRole('Manager')")
}
