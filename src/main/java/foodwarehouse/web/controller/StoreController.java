package foodwarehouse.web.controller;

import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.service.*;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.response.product.ProductResponse;
import foodwarehouse.web.response.product.StoreProductResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final ProductBatchService productBatchService;
    private final ProductService productService;
    private final ProductInStorageService productInStorageService;
    private final ConnectionService connectionService;

    public StoreController(
            ProductBatchService productBatchService,
            ProductService productService,
            ProductInStorageService productInStorageService,
            ConnectionService connectionService) {
        this.productBatchService = productBatchService;
        this.productService = productService;
        this.productInStorageService = productInStorageService;
        this.connectionService = connectionService;
    }

    @GetMapping("/products")
    public SuccessResponse<List<StoreProductResponse>> getProductsForCustomers() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        List<StoreProductResponse> result = new LinkedList<>();

        List<Product> availableProducts = productService.findAvailableProducts();

        for(Product p : availableProducts) {
            int quantity = productService.countAmountOfProduct(p.productId());
            List<ProductBatch> discountedProducts = productBatchService
                    .findProductBatchesWithDiscountAndProductId(p.productId());

            List<Integer> discountedProductsQuantity = new LinkedList<>();

            for(ProductBatch pb : discountedProducts) {
                discountedProductsQuantity.add(productBatchService.countProductBatchAmount(pb.batchId()));
            }

            result.add(StoreProductResponse.fromProductAndDiscountList(p, discountedProducts, discountedProductsQuantity, quantity));
        }
        return new SuccessResponse<>(result);
    }

    @GetMapping("/product/{id}")
    public SuccessResponse<StoreProductResponse> getProductById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        int quantity = productService.countAmountOfProduct(id);
        List<ProductBatch> discountedProducts = productBatchService
                .findProductBatchesWithDiscountAndProductId(id);

        List<Integer> discountedProductsQuantity = new LinkedList<>();

        for(ProductBatch pb : discountedProducts) {
            discountedProductsQuantity.add(productBatchService.countProductBatchAmount(pb.batchId()));
        }

        Product product = productService
                .findProductById(id)
                .orElseThrow(() -> new RestException("Cannot find product with this ID."));

        return new SuccessResponse<>(
                StoreProductResponse
                        .fromProductAndDiscountList(
                                product,
                                discountedProducts,
                                discountedProductsQuantity,
                                quantity));
    }
}
