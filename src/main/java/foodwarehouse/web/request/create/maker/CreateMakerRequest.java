package foodwarehouse.web.request.create.maker;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.create.CreateAddressRequest;

public record CreateMakerRequest(
        @JsonProperty(value = "maker_data", required = true)     CreateMakerData createMakerData,
        @JsonProperty(value = "address", required = true)CreateAddressRequest createAddressRequest) {
}
