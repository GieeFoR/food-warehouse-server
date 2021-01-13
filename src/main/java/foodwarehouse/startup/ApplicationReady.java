package foodwarehouse.startup;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.service.ProductBatchService;
import foodwarehouse.core.service.ProductInStorageService;
import foodwarehouse.core.service.ProductService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class ApplicationReady implements ApplicationListener<ApplicationReadyEvent> {

    private final ProductInStorageService productInStorageService;
    private final ProductService productService;
    private final ProductBatchService productBatchService;

    public ApplicationReady(ProductInStorageService productInStorageService, ProductService productService, ProductBatchService productBatchService) {
        this.productInStorageService = productInStorageService;
        this.productService = productService;
        this.productBatchService = productBatchService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
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
        System.out.println("Calculating discounts has ended!");

        System.out.println("Looking for expired products batches!");
        ExpiredBatches.storeExpiredBatches(productInStorageService.findExpiredProductsInStorages());
        System.out.println("Amount of found expired batches: " + ExpiredBatches.getExpiredBatches().size());

        System.out.println("Looking for running out products!");
        RunningOutProducts.storeRunningOutProducts(productService.findRunningOutProducts());
        System.out.println("Amount of found running out products: " + RunningOutProducts.getRunningOutProducts().size());
    }
}
