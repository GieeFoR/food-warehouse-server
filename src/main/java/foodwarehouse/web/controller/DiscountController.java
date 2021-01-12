package foodwarehouse.web.controller;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.ProductBatchService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/discount")
public class DiscountController {

    private final ProductBatchService productBatchService;
    private final ConnectionService connectionService;

    public DiscountController(ProductBatchService productBatchService, ConnectionService connectionService) {
        this.productBatchService = productBatchService;
        this.connectionService = connectionService;
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<Void> calculateDiscounts() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        System.out.println("Calculating discounts!");

        LocalDate now = LocalDate.now();
        LocalDate startDiscount = now.plusDays(3);
        LocalDate endDiscount = now.plusDays(15);

        List<ProductBatch> batches = productBatchService.findProductBatches();

        for(ProductBatch pb : batches) {
            LocalDate productEatByDate = pb.eatByDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(productEatByDate.isBefore(endDiscount) && productEatByDate.isAfter(startDiscount)) {
                long temp = productEatByDate.toEpochDay() - now.toEpochDay() - 4L;
                int discount = 20 + 5 * (10 - (int) temp);
                productBatchService.updateProductBatch(pb.batchId(), pb.product(), pb.batchNumber(), pb.eatByDate(), discount, pb.packagesQuantity());
            }
        }
        return new SuccessResponse<>(null);
    }
}
