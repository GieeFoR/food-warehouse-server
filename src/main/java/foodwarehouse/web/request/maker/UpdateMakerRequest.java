package foodwarehouse.web.request.maker;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.address.UpdateAddressRequest;

public record UpdateMakerRequest(
        @JsonProperty(value = "maker_data", required = true)        UpdateMakerData updateMakerData,
        @JsonProperty(value = "address", required = true)           UpdateAddressRequest updateAddressRequest) {
}
