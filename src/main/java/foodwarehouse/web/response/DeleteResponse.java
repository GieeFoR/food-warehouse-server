package foodwarehouse.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeleteResponse(
        @JsonProperty(value = "deleted", required = true)   boolean deleted) {

    public static DeleteResponse fromBoolean(boolean deleted) {
        return new DeleteResponse(deleted);
    }
}
