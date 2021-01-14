package foodwarehouse.web.response.alert;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.product.Product;

public record RunningOutProductsAlert(
        @JsonProperty(value = "content", required = true)           String content,
        @JsonProperty(value = "product_id", required = true)        int productId) {

    public static RunningOutProductsAlert fromProduct(Product product) {
        return new RunningOutProductsAlert(
                String.format("Kończy się zapas produktu \"%s\" o id %s w cenie regularnej. " +
                        "Rozważ uzupełnienie stanu magazynowego tego produktu.",
                        product.name(),
                        product.productId()),
                product.productId());
    }
}
