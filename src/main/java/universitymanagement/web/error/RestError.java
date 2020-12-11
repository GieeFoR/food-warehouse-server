package universitymanagement.web.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RestError(@JsonProperty("message") String message) {
}
