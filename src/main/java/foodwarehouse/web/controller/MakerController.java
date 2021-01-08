package foodwarehouse.web.controller;

import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.MakerService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.response.maker.MakerResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/producer")
public class MakerController {

    private final MakerService makerService;
    private final ConnectionService connectionService;

    public MakerController(MakerService makerService, ConnectionService connectionService) {
        this.makerService = makerService;
        this.connectionService = connectionService;
    }

//    @GetMapping
//    @PreAuthorize("hasRole('Admin')")
//    public SuccessResponse<List<MakerResponse>> getMakers() {
//
//    }

}
