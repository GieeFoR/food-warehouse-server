package foodwarehouse.web.request.maker;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateMakerData(
        @JsonProperty(value = "maker_id", required = true)      int makerId,
        @JsonProperty(value = "firm_name", required = true)     String firmName,
        @JsonProperty(value = "phone", required = true)         String phoneNumber,
        @JsonProperty(value = "email", required = true)         String email) {
}
