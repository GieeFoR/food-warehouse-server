package foodwarehouse.web.controller.supplier;

import foodwarehouse.core.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController {

    private final UserService userService;

    public SupplierController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasRole('Supplier')")
}
