package foodwarehouse.web.component;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.service.*;
import foodwarehouse.startup.ExpiredBatches;
import foodwarehouse.startup.RunningOutProducts;
import foodwarehouse.startup.StoragesRunningOutOfSpace;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class Watchdog {

    private final ProductBatchService productBatchService;
    private final ProductInStorageService productInStorageService;
    private final ProductService productService;
    private final ConnectionService connectionService;
    private final StorageService storageService;

    public Watchdog(ProductBatchService productBatchService, ProductInStorageService productInStorageService, ProductService productService, ConnectionService connectionService, StorageService storageService) {
        this.productBatchService = productBatchService;
        this.productInStorageService = productInStorageService;
        this.productService = productService;
        this.connectionService = connectionService;
        this.storageService = storageService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkDiscount() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            return;
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
        System.out.println("Calculating discounts has ended!");

        ExpiredBatches.clear();
        System.out.println("Looking for expired products batches!");
        ExpiredBatches.storeExpiredBatches(productInStorageService.findExpiredProductsInStorages());
        System.out.println("Amount of found expired batches: " + ExpiredBatches.getExpiredBatches().size());

        RunningOutProducts.clear();
        System.out.println("Looking for running out products!");
        RunningOutProducts.storeRunningOutProducts(productService.findRunningOutProducts());
        System.out.println("Amount of found running out products: " + RunningOutProducts.getRunningOutProducts().size());

        StoragesRunningOutOfSpace.clear();
        System.out.println("Looking for storages running out space!");
        StoragesRunningOutOfSpace.storeRunningOutOfSpace(storageService.findStoragesRunningOutOfSpace());
        System.out.println("Amount of found storages running out space: " + StoragesRunningOutOfSpace.getRunningOutOfSpace().size());
    }
}
