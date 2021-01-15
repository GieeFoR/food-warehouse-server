package foodwarehouse.web.response.alert;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

public record EmployeeAlertResponse(
        @JsonProperty(value = "expiring_batches", required = true)                  List<ExpiringBatchAlert>expiringBatchesAlerts,
        @JsonProperty(value = "storages_running_out_of_space", required = true)     List<StoragesRunningOutOfSpaceAlert> storagesRunningOutOfSpaceAlerts) {

    public static EmployeeAlertResponse fromLists(List<ProductInStorage> expiredBatches, List<Storage> runningOutOfSpace) {
        return new EmployeeAlertResponse(
                expiredBatches.stream().map(ExpiringBatchAlert::fromProductInStorage).collect(Collectors.toList()),
                runningOutOfSpace.stream().map(StoragesRunningOutOfSpaceAlert::fromStorage).collect(Collectors.toList()));
    }
}
