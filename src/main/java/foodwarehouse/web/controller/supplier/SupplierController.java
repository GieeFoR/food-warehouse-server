package foodwarehouse.web.controller.supplier;

import foodwarehouse.core.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController {

    private final UserService userService;

    public SupplierController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasRole('Supplier')")
}
