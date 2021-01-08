package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateMakerData(
        @JsonProperty(value = "firm_name", required = true)     String firmName,
        @JsonProperty(value = "phone", required = true)         String phoneNumber,
        @JsonProperty(value = "email", required = true)         String email) {
}
