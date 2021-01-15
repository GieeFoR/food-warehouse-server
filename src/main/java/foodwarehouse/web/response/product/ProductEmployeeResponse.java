package foodwarehouse.web.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.product.Product;

public record ProductEmployeeResponse(
        @JsonProperty(value = "product", required = true)       ProductDataResponse productDataResponse) {

    public static ProductEmployeeResponse fromProduct(Product product) {
        return new ProductEmployeeResponse(
                ProductDataResponse.fromProduct(product));
    }
}
