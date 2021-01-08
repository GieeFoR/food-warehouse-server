package foodwarehouse.web.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.product.Product;
import foodwarehouse.web.response.maker.MakerResponse;

public record ProductResponse(
        @JsonProperty(value = "product", required = true)       ProductDataResponse productDataResponse,
        @JsonProperty(value = "maker", required = true)         MakerResponse makerResponse) {

    public static ProductResponse fromProduct(Product product) {
        return new ProductResponse(
                ProductDataResponse.fromProduct(product),
                MakerResponse.fromMaker(product.maker()));
    }
}
