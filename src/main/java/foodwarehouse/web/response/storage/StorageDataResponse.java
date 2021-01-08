package foodwarehouse.web.response.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.storage.Storage;

public record StorageDataResponse(
        @JsonProperty(value = "storage_id", required = true)        int storageId,
        @JsonProperty(value = "name", required = true)              String storageName,
        @JsonProperty(value = "capacity", required = true)          int capacity,
        @JsonProperty(value = "is_cold_storage", required = true)   boolean isColdStorage) {

    public static StorageDataResponse fromStorage(Storage storage) {
        return new StorageDataResponse(
                storage.storageId(),
                storage.storageName(),
                storage.capacity(),
                storage.isColdStorage());
    }
}
