package universitymanagement.web.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(@JsonProperty("error") RestError error) {
}
