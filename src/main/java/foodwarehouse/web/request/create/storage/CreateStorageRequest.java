package foodwarehouse.web.request.create.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.create.CreateAddressRequest;

public record CreateStorageRequest(
        @JsonProperty(value = "manager_id", required = true)        int managerId,
        @JsonProperty(value = "storage", required = true)           CreateStorageData createStorageData,
        @JsonProperty(value = "address", required = true)           CreateAddressRequest createAddressRequest) {
}
