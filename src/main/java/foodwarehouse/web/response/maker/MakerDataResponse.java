package foodwarehouse.web.response.maker;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.maker.Maker;

public record MakerDataResponse(
        @JsonProperty(value = "maker_id", required = true)      int makerId,
        @JsonProperty(value = "firm_name", required = true)     String firmName,
        @JsonProperty(value = "phone", required = true)         String phoneNumber,
        @JsonProperty(value = "email", required = true)         String email) {

    public static MakerDataResponse fromMaker(Maker maker) {
        return new MakerDataResponse(
                maker.makerId(),
                maker.firmName(),
                maker.phoneNumber(),
                maker.email());
    }
}
