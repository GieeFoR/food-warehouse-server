package foodwarehouse.web.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateMakerRequest(
        @JsonProperty(value = "maker", required = true)     UpdateMakerData updateMakerData,
        @JsonProperty(value = "address", required = true)   UpdateAddressRequest updateAddressRequest) {
}
