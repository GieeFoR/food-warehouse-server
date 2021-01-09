package foodwarehouse.web.request.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateStorageData(
        @JsonProperty(value = "name", required = true)              String storageName,
        @JsonProperty(value = "capacity", required = true)          int capacity,
        @JsonProperty(value = "is_cold_storage", required = true)   boolean isColdStorage) {
}
