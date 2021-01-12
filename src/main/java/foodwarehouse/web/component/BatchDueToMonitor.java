package foodwarehouse.web.component;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.ProductBatchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchDueToMonitor {

    private final ProductBatchService productBatchService;
    private final ConnectionService connectionService;

    public BatchDueToMonitor(ProductBatchService productBatchService, ConnectionService connectionService) {
        this.productBatchService = productBatchService;
        this.connectionService = connectionService;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void checkDueTo() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            return;
        }

        List<ProductBatch> batches = productBatchService.findProductBatches();


    }
}
