package foodwarehouse.web.request.maker;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.address.CreateAddressRequest;

public record CreateMakerRequest(
        @JsonProperty(value = "maker_data", required = true)     CreateMakerData createMakerData,
        @JsonProperty(value = "address", required = true)        CreateAddressRequest createAddressRequest) {
}
