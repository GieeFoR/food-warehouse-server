package foodwarehouse.web.response.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.web.response.employee.EmployeeResponse;
import foodwarehouse.web.response.storage.StorageDataResponse;

public record StorageBatchResponse(
        @JsonProperty(value = "manager", required = true)       EmployeeResponse employeeResponse,
        @JsonProperty(value = "storage", required = true)       StorageDataResponse storageDataResponse,
        @JsonProperty(value = "address", required = true)       foodwarehouse.web.response.address.AddressResponse AddressResponse,
        @JsonProperty(value = "quantity", required = true)      int quantity) {

    public static StorageBatchResponse from(Storage storage, int quantity) {
        return new StorageBatchResponse(
                EmployeeResponse.fromEmployee(storage.manager()),
                StorageDataResponse.fromStorage(storage),
                foodwarehouse.web.response.address.AddressResponse.fromAddress(storage.address()),
                quantity);
    }
}
