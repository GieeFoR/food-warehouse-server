package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.web.response.address.AddressResponse;

import java.util.Date;

public record OrderDeliveryResponse(
        @JsonProperty(value = "delivery_id", required = true)           int deliveryId,
        @JsonProperty(value = "remove_from_storage_date")               Date removeFromStorage,
        @JsonProperty(value = "delivery_date")                          Date deliveryDate,
        @JsonProperty(value = "address", required = true)               AddressResponse addressResponse) {

    public static OrderDeliveryResponse fromDelivery(Delivery delivery) {
        return new OrderDeliveryResponse(
                delivery.deliveryId(),
                delivery.removalFromStorage(),
                delivery.deliveryDate(),
                AddressResponse.fromAddress(delivery.address()));
    }
}
