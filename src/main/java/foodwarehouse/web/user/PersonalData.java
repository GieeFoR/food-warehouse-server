package foodwarehouse.web.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonalData(
        @JsonProperty("name")           String name,
        @JsonProperty("surname")        String surname,
        @JsonProperty("phoneNumber")    String phoneNumber,
        @JsonProperty("firmName")       String firmName,
        @JsonProperty("tax_id")         String tax_id) {
}
