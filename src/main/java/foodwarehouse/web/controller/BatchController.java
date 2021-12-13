package foodwarehouse.web.controller;

import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.core.service.*;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.batch.CreateProductInStorageRequest;
import foodwarehouse.web.request.batch.DeleteProductFromStorageRequest;
import foodwarehouse.web.response.batch.BatchResponse;
import foodwarehouse.web.response.batch.EmployeeBatchResponse;
import foodwarehouse.web.response.batch.StorageBatchResponse;
import foodwarehouse.web.response.product.ProductEmployeeResponse;
import foodwarehouse.web.response.product.ProductResponse;
import foodwarehouse.web.response.storage.StorageEmployeeResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/batch")
public class BatchController {

    private final ConnectionService connectionService;
    private final EmployeeService employeeService;
    private final ProductInStorageService productInStorageService;
    private final ProductBatchService productBatchService;
    private final StorageService storageService;
    private final ProductService productService;


    public BatchController(ConnectionService connectionService, EmployeeService employeeService, ProductInStorageService productInStorageService, ProductBatchService productBatchService, StorageService storageService, ProductService productService) {
        this.connectionService = connectionService;
        this.employeeService = employeeService;
        this.productInStorageService = productInStorageService;
        this.productBatchService = productBatchService;
        this.storageService = storageService;
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Employee')")
    public SuccessResponse<List<BatchResponse>> getProductsInStorages() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<ProductInStorage> productInStorageList = productInStorageService.findProductInStorageAll();
        List<BatchResponse> result = new LinkedList<>();

        for(ProductInStorage productInStorage : productInStorageList) {
            Optional<BatchResponse> productBatchTemp = result.stream().filter(o -> o.batchId() == productInStorage.batch().batchId()).findFirst();
            if(productBatchTemp.isPresent()) {
                productBatchTemp.get().storageBatchResponse().add(StorageBatchResponse.from(productInStorage.storage(), productInStorage.quantity()));
            }
            else {
                result.add(
                        new BatchResponse(
                                productInStorage.batch().batchId(),
                                productInStorage.batch().batchNumber(),
                                new LinkedList<>(Collections.singleton(StorageBatchResponse.from(productInStorage.storage(), productInStorage.quantity()))),
                                ProductResponse.fromProduct(productInStorage.batch().product()),
                                sdf.format(productInStorage.batch().eatByDate()),
                                productInStorage.batch().discount(),
                                productInStorage.quantity(),
                                productInStorage.batch().packagesQuantity()));
            }
        }
        return new SuccessResponse<>(result);
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('Employee')")
    public SuccessResponse<List<EmployeeBatchResponse>> getProductsInStoragesEmployee() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<ProductInStorage> productInStorageList = productInStorageService.findProductInStorageAll();
        List<EmployeeBatchResponse> result = new LinkedList<>();

        for(ProductInStorage productInStorage : productInStorageList) {
            Optional<EmployeeBatchResponse> productBatchTemp = result.stream().filter(o -> o.batchId() == productInStorage.batch().batchId()).findFirst();
            if(productBatchTemp.isPresent()) {
                productBatchTemp.get().storagesEmployeeResponse().add(StorageEmployeeResponse.fromStorage(productInStorage.storage(), productInStorage.quantity()));
            }
            else {
                result.add(
                        new EmployeeBatchResponse(
                                productInStorage.batch().batchId(),
                                productInStorage.batch().batchNumber(),
                                new LinkedList<>(Collections.singleton(StorageEmployeeResponse.fromStorage(productInStorage.storage(), productInStorage.quantity()))),
                                ProductEmployeeResponse.fromProduct(productInStorage.batch().product()),
                                sdf.format(productInStorage.batch().eatByDate()),
                                productInStorage.quantity(),
                                productInStorage.batch().discount(),
                                productInStorage.batch().packagesQuantity()));
            }
        }
        return new SuccessResponse<>(result);
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Employee')")
    public SuccessResponse<Void> createProductsInStorages(@RequestBody CreateProductInStorageRequest request) throws ParseException {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Optional<ProductBatch> productBatches = productBatchService.findProductBatches().stream().filter(o -> o.batchNumber() == request.batchNo()).findFirst();
        Storage storage = storageService.findStorage(request.storageId())
                .orElseThrow(() -> new RestException("Cannot find storage."));

        if(productBatches.isPresent()) {
            Optional <ProductInStorage> productInStorage = productInStorageService.findProductInStorageById(productBatches.get(), storage);
            if(productInStorage.isPresent()) {
                productInStorageService.updateProductInStorage(
                        productBatches.get(),
                        storage,
                        productInStorage.get().quantity() + request.quantity());
            }
            else {
                productInStorageService.createProductInStorage(
                        productBatches.get(),
                        storage,
                        request.quantity());
            }
        }
        else {
            Product product = productService
                    .findProductById(request.productId())
                    .orElseThrow(() -> new RestException("Cannot find product."));

            ProductBatch productBatch = productBatchService.createProductBatch(
                    product,
                    request.batchNo(),
                    sdf.parse(request.eatByDate()),
                    request.packagesQuantity())
                    .orElseThrow(() -> new RestException("Cannot create product batch."));

            productInStorageService.createProductInStorage(
                    productBatch,
                    storage,
                    request.quantity());
        }

        return new SuccessResponse<>(null);
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Employee')")
    public SuccessResponse<Void> updateProductsInStorages(@RequestBody CreateProductInStorageRequest request) throws ParseException {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ProductBatch productBatch = productBatchService.findProductBatchById(request.batchId())
                .orElseThrow(() -> new RestException("Cannot find batch."));
        Storage storage = storageService.findStorage(request.storageId())
                .orElseThrow(() -> new RestException("Cannot find storage."));
        Product product = productService.findProductById(request.productId())
                .orElseThrow(() -> new RestException("Cannot find product."));

        productBatchService.updateProductBatch(
                request.batchId(),
                product,
                request.batchNo(),
                sdf.parse(request.eatByDate()),
                productBatch.discount(),
                request.packagesQuantity());

        productInStorageService.updateProductInStorage(
                productBatch,
                storage,
                request.quantity());

        return new SuccessResponse<>(null);
    }

    @PutMapping("/delete")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Employee')")
    public SuccessResponse<Void> deleteProductsFromStorage(@RequestBody DeleteProductFromStorageRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        ProductBatch productBatch = productBatchService.findProductBatchById(request.batchId())
                .orElseThrow(() -> new RestException("Cannot find batch."));
        Storage storage = storageService.findStorage(request.storageId())
                .orElseThrow(() -> new RestException("Cannot find storage."));

        productInStorageService.updateProductInStorage(
                productBatch,
                storage,
                0);

        return new SuccessResponse<>(null);
    }

    @PutMapping("/delete-many")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager') || hasRole('Employee')")
    public SuccessResponse<Void> deleteListOfProductsFromStorages(@RequestBody List<Integer> request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        for(Integer batch : request) {
            List<ProductInStorage> productInStorage = productInStorageService.findProductInStorageAllByBatchId(batch);

            for(ProductInStorage product : productInStorage) {
                productInStorageService.updateProductInStorage(
                        product.batch(),
                        product.storage(),
                        0);
            }
        }

        return new SuccessResponse<>(null);
    }
}
