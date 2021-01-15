package foodwarehouse.web.request.batch;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateProductInStorage(
        @JsonProperty(value = "batch_id")                               int batchId,
        @JsonProperty(value = "batch_no", required = true)              int batchNo,
        @JsonProperty(value = "eat_by_date", required = true)           String eatByDate,
        @JsonProperty(value = "product_id", required = true)            int productId,
        @JsonProperty(value = "packages_quantity", required = true)     int packagesQuantity,
        @JsonProperty(value = "storage_id", required = true)            int storageId,
        @JsonProperty(value = "quantity", required = true)              int quantity) {
}
