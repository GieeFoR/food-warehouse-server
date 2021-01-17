package foodwarehouse.web.request.batch;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeleteProductFromStorageRequest(
        @JsonProperty(value = "batch_id", required = true)              int batchId,
        @JsonProperty(value = "storage_id", required = true)            int storageId) {
}
