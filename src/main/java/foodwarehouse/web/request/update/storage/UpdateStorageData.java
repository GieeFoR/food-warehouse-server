package foodwarehouse.web.request.update.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateStorageData(
        @JsonProperty(value = "storage_id", required = true)        int storageId,
        @JsonProperty(value = "name", required = true)              String storageName,
        @JsonProperty(value = "capacity", required = true)          int capacity,
        @JsonProperty(value = "is_cold_storage", required = true)   boolean isColdStorage
) {
}
