package foodwarehouse.web.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateMakerRequest(
        @JsonProperty(value = "maker", required = true)     CreateMakerData createMakerData,
        @JsonProperty(value = "address", required = true)   CreateAddressRequest createAddressRequest) {
}
