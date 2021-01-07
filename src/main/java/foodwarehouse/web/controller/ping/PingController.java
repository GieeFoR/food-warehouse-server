package foodwarehouse.web.controller.ping;

import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.response.PingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public SuccessResponse<PingResponse> getPing() {
        return new SuccessResponse<>(
                new PingResponse(true)
        );
    }
}