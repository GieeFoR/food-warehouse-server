package foodwarehouse.web.controller;

import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.service.*;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.create.CreateProductRequest;
import foodwarehouse.web.request.update.UpdateProductRequest;
import foodwarehouse.web.response.DeleteResponse;
import foodwarehouse.web.response.product.ProductResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final MakerService makerService;
    private final ConnectionService connectionService;

    public ProductController(ProductService productService, MakerService makerService, ConnectionService connectionService) {
        this.productService = productService;
        this.makerService = makerService;
        this.connectionService = connectionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<ProductResponse>> getProducts() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var cars = productService
                .findProducts()
                .stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());

        return new SuccessResponse<>(cars);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<ProductResponse> getCarById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return productService
                .findProductById(id)
                .map(ProductResponse::fromProduct)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find product with this ID."));
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<ProductResponse> createCar(@RequestBody CreateProductRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Maker maker = makerService
                .findMakerById(request.makerId())
                .orElseThrow(() -> new RestException("Cannot find producer."));

        return productService
                .createProduct(
                        maker,
                        request.name(),
                        request.category(),
                        request.needColdStorage(),
                        request.buyPrice(),
                        request.sellPrice())
                .map(ProductResponse::fromProduct)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a new product."));
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<ProductResponse> updateCar(@RequestBody UpdateProductRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Maker maker = makerService
                .findMakerById(request.makerId())
                .orElseThrow(() -> new RestException("Cannot find producer."));

        return productService
                .updateProduct(
                        request.productId(),
                        maker,
                        request.name(),
                        request.category(),
                        request.needColdStorage(),
                        request.buyPrice(),
                        request.sellPrice())
                .map(ProductResponse::fromProduct)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update a product."));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<DeleteResponse>> deleteCars(@RequestBody List<Integer> request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        List<DeleteResponse> result = new LinkedList<>();
        for(int i : request) {
            result.add(
                    new DeleteResponse(
                            productService.deleteProduct(i)));
        }

        return new SuccessResponse<>(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<DeleteResponse> deleteCarById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return new SuccessResponse<>(
                new DeleteResponse(
                        productService.deleteProduct(id)));
    }
}
