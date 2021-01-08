package foodwarehouse.web.request.update.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.update.UpdateAddressRequest;

public record UpdateStorageRequest(
        @JsonProperty(value = "manager_id", required = true)        int managerId,
        @JsonProperty(value = "storage", required = true)           UpdateStorageData updateStorageData,
        @JsonProperty(value = "address", required = true)           UpdateAddressRequest updateAddressRequest) {
}
