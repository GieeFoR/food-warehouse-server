package foodwarehouse.web.response.alert;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

public record AlertResponse(
    @JsonProperty(value = "expiring_batches", required = true)                  List<ExpiringBatchAlert> expiringBatchesAlerts,
    @JsonProperty(value = "running_out_products", required = true)              List<RunningOutProductsAlert> runningOutProductsAlerts,
    @JsonProperty(value = "storages_running_out_of_space", required = true)     List<StoragesRunningOutOfSpaceAlert> storagesRunningOutOfSpaceAlerts) {

    public static AlertResponse fromLists(List<ProductInStorage> expiredBatches, List<Product> runningOutProducts, List<Storage> runningOutOfSpace) {
        return new AlertResponse(
                expiredBatches.stream().map(ExpiringBatchAlert::fromProductInStorage).collect(Collectors.toList()),
                runningOutProducts.stream().map(RunningOutProductsAlert::fromProduct).collect(Collectors.toList()),
                runningOutOfSpace.stream().map(StoragesRunningOutOfSpaceAlert::fromStorage).collect(Collectors.toList()));
    }
}
