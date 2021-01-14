package foodwarehouse.web.controller;

import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.ProductInStorageService;
import foodwarehouse.core.service.ProductService;
import foodwarehouse.core.service.StorageService;
import foodwarehouse.startup.ExpiredBatches;
import foodwarehouse.startup.RunningOutProducts;
import foodwarehouse.startup.StoragesRunningOutOfSpace;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alert")
public class AlertsController {

    private final ProductInStorageService productInStorageService;
    private final ProductService productService;
    private final ConnectionService connectionService;
    private final StorageService storageService;

    public AlertsController(ProductInStorageService productInStorageService, ProductService productService, ConnectionService connectionService, StorageService storageService) {
        this.productInStorageService = productInStorageService;
        this.productService = productService;
        this.connectionService = connectionService;
        this.storageService = storageService;
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<Void> updateAlerts() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        System.out.println("Looking for expired products batches!");
        ExpiredBatches.storeExpiredBatches(productInStorageService.findExpiredProductsInStorages());
        System.out.println("Amount of found expired batches: " + ExpiredBatches.getExpiredBatches().size());

        System.out.println("Looking for running out products!");
        RunningOutProducts.storeRunningOutProducts(productService.findRunningOutProducts());
        System.out.println("Amount of found running out products: " + RunningOutProducts.getRunningOutProducts().size());

        System.out.println("Looking for storages running out space!");
        StoragesRunningOutOfSpace.storeRunningOutOfSpace(storageService.findStoragesRunningOutOfSpace());
        System.out.println("Amount of found storages running out space: " + StoragesRunningOutOfSpace.getRunningOutOfSpace().size());

        return new SuccessResponse<>(null);
    }
}
