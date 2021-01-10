package foodwarehouse.web.controller;

import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.service.*;
import foodwarehouse.database.NoImageTemplate;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.product.CreateProductRequest;
import foodwarehouse.web.request.product.UpdateProductRequest;
import foodwarehouse.web.response.others.DeleteResponse;
import foodwarehouse.web.response.product.DiscountResponse;
import foodwarehouse.web.response.product.ProductResponse;
import foodwarehouse.web.response.product.StoreProductResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;
    private final MakerService makerService;
    private final ConnectionService connectionService;

    public ProductController(ProductService productService, MakerService makerService, ConnectionService connectionService) {
        this.productService = productService;
        this.makerService = makerService;
        this.connectionService = connectionService;
    }

    @GetMapping("/product")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<ProductResponse>> getProducts() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var product = productService
                .findProducts()
                .stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());

        return new SuccessResponse<>(product);
    }

    @GetMapping("/store/products")
    public SuccessResponse<List<StoreProductResponse>> getProductsForCustomers() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

//        final var product = productService
//                .findProducts()
//                .stream()
//                .map(StoreProductResponse::fromProduct)
//                .collect(Collectors.toList());

        List<StoreProductResponse> result = new LinkedList<>();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        List<DiscountResponse> discountResponses = new LinkedList<>();
        try {
            discountResponses.add(new DiscountResponse(sdf.parse("2021-05-12"), 100));
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        result.add(new StoreProductResponse(
                        1,
                "ziemniorek",
                "pyszny",
                "Pan Małkowicz byłby bardzo zadowolony z takiego ziemniaczka polanego masełkiem i zjedzonego w Alpach.",
                "Warzywa",
                false,
                (float) 2.5,
                NoImageTemplate.image(),
                "Rolnik",
                100,
                new LinkedList<DiscountResponse>()));

        result.add(new StoreProductResponse(
                1,
                "marchewaczka",
                "Jest pomarańczowa i bogata w karoten",
                "Karol Okrasa by taką chciał.",
                "Warzywa",
                false,
                (float) 200.0,
                NoImageTemplate.image(),
                "Rolnik",
                1000,
                discountResponses));

        return new SuccessResponse<>(result);
    }

    @GetMapping("/product/{id}")
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

    @PostMapping("/product")
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
                        request.shortDesc(),
                        request.longDesc(),
                        request.category(),
                        request.needColdStorage(),
                        request.buyPrice(),
                        request.sellPrice(),
                        request.image())
                .map(ProductResponse::fromProduct)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a new product."));
    }

    @PutMapping("/product")
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
                        request.shortDesc(),
                        request.longDesc(),
                        request.category(),
                        request.needColdStorage(),
                        request.buyPrice(),
                        request.sellPrice(),
                        request.image())
                .map(ProductResponse::fromProduct)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update a product."));
    }

    @DeleteMapping("/product")
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

    @DeleteMapping("/product/{id}")
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
