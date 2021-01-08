package foodwarehouse.web.controller;

import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.MakerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class MakerController {

    private final MakerService makerService;
    private final ConnectionService connectionService;

    public MakerController(MakerService makerService, ConnectionService connectionService) {
        this.makerService = makerService;
        this.connectionService = connectionService;
    }



}
