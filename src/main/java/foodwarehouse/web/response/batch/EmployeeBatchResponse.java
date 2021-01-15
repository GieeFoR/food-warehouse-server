package foodwarehouse.web.response.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.response.product.ProductEmployeeResponse;
import foodwarehouse.web.response.storage.StorageEmployeeResponse;

import java.util.List;

public record EmployeeBatchResponse(
        @JsonProperty(value = "batch_id", required = true)          int batchId,
        @JsonProperty(value = "batch_no", required = true)          int batchNumber,
        @JsonProperty(value = "storages", required = true)          List<StorageEmployeeResponse> storagesEmployeeResponse,
        @JsonProperty(value = "product", required = true)           ProductEmployeeResponse productEmployeeResponse,
        @JsonProperty(value = "eat_by_date", required = true)       String eatByDate,
        @JsonProperty(value = "quantity", required = true)          int quantity,
        @JsonProperty(value = "discount", required = true)          int discount) {
}
