package foodwarehouse.web.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.productBatch.ProductBatch;

import java.util.LinkedList;
import java.util.List;

public record StoreProductResponse(
        @JsonProperty(value = "product_id", required = true)    int productId,
        @JsonProperty(value = "name", required = true)          String name,
        @JsonProperty(value = "short_description")              String shortDesc,
        @JsonProperty(value = "description")                    String longDesc,
        @JsonProperty(value = "category", required = true)      String category,
        @JsonProperty(value = "needs_cold", required = true)    boolean needColdStorage,
        @JsonProperty(value = "sell_price", required = true)    float sellPrice,
        @JsonProperty(value = "image", required = true)         String image,
        @JsonProperty(value = "producer_name", required = true) String producerName,
        @JsonProperty(value = "quantity", required = true)      int quantity,
        @JsonProperty(value = "discounts", required = true)     List<DiscountResponse>discounts) {

    public static StoreProductResponse fromProductAndDiscountList(
            Product product,
            List<ProductBatch> discounts,
            List<Integer> discountedProductsQuantity,
            int amountOfProductInRegularPrice) {

        List<DiscountResponse> discountsResponse = new LinkedList<>();

        int counter = 0;
        for(ProductBatch pb : discounts) {
            discountsResponse.add(DiscountResponse.fromProductBatch(pb, discountedProductsQuantity.get(counter), product.sellPrice()));
            counter++;
        }

        return new StoreProductResponse(
                product.productId(),
                product.name(),
                product.shortDesc(),
                product.longDesc(),
                product.category(),
                product.needColdStorage(),
                product.sellPrice(),
                product.image(),
                product.maker().firmName(),
                amountOfProductInRegularPrice,
                discountsResponse
        );
    }
}
