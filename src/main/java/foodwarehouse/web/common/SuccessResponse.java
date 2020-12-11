package foodwarehouse.web.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SuccessResponse<T>(@JsonProperty("result") T result) {
}
