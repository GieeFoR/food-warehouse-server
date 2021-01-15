package foodwarehouse.web.response.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.response.product.ProductResponse;
import foodwarehouse.web.response.storage.StorageResponse;

import java.util.List;

public record BatchResponse(
        @JsonProperty(value = "batch_id", required = true)          int batchId,
        @JsonProperty(value = "batch_no", required = true)          int batchNumber,
        @JsonProperty(value = "storages", required = true)          List<StorageResponse> storagesResponse,
        @JsonProperty(value = "product", required = true)           ProductResponse productResponse,
        @JsonProperty(value = "eat_by_date", required = true)       String eatByDate,
        @JsonProperty(value = "discount", required = true)          int discount,
        @JsonProperty(value = "quantity", required = true)          int quantity) {
}
