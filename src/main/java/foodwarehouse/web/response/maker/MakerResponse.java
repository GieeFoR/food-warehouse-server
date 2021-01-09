package foodwarehouse.web.response.maker;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.web.response.address.AddressResponse;

public record MakerResponse(
        @JsonProperty(value = "maker_data", required = true)    MakerDataResponse makerDataResponse,
        @JsonProperty(value = "address", required = true)       AddressResponse addressResponse) {

    public static MakerResponse fromMaker(Maker maker) {
        return new MakerResponse(
                MakerDataResponse.fromMaker(maker),
                AddressResponse.fromAddress(maker.address()));
    }
}
