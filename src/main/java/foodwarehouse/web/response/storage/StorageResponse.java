package foodwarehouse.web.response.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.web.response.address.AddressResponse;
import foodwarehouse.web.response.employee.EmployeeResponse;

public record StorageResponse(
        @JsonProperty(value = "manager", required = true)       EmployeeResponse employeeResponse,
        @JsonProperty(value = "storage", required = true)       StorageDataResponse storageDataResponse,
        @JsonProperty(value = "address", required = true)       AddressResponse AddressResponse) {

    public static StorageResponse fromStorage(Storage storage) {
        return new StorageResponse(
                EmployeeResponse.fromEmployee(storage.manager()),
                StorageDataResponse.fromStorage(storage),
                foodwarehouse.web.response.address.AddressResponse.fromAddress(storage.address()));
    }
}
