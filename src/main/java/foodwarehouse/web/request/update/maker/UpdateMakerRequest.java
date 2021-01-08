package foodwarehouse.web.request.update.maker;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.update.UpdateAddressRequest;

public record UpdateMakerRequest(
        @JsonProperty(value = "maker_data", required = true)        UpdateMakerData updateMakerData,
        @JsonProperty(value = "address", required = true)           UpdateAddressRequest updateAddressRequest) {
}
