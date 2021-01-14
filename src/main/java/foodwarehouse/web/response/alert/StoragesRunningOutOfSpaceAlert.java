package foodwarehouse.web.response.alert;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.storage.Storage;

public record StoragesRunningOutOfSpaceAlert(
        @JsonProperty(value = "content", required = true)       String content,
        @JsonProperty(value = "storage_id", required = true)    int storageId) {

    public static StoragesRunningOutOfSpaceAlert fromStorage(Storage storage) {
        return new StoragesRunningOutOfSpaceAlert(
                String.format("Kończy się wolna przestrzeń w magazynie o nazwie \"%s\" oraz id %d",
                        storage.storageName(),
                        storage.storageId()),
                storage.storageId());
    }
}
