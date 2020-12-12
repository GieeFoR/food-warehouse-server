package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmailResponse(@JsonProperty("emailExists") boolean emailExists) {
}
