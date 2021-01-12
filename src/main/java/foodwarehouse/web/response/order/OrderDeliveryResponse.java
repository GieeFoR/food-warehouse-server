package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.delivery.Delivery;
import foodwarehouse.web.response.address.AddressResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public record OrderDeliveryResponse(
        @JsonProperty(value = "delivery_id", required = true)           int deliveryId,
        @JsonProperty(value = "remove_from_storage_date")               String removeFromStorage,
        @JsonProperty(value = "delivery_date")                          String deliveryDate,
        @JsonProperty(value = "address", required = true)               AddressResponse addressResponse) {

    public static OrderDeliveryResponse fromDelivery(Delivery delivery) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new OrderDeliveryResponse(
                delivery.deliveryId(),
                sdf.format(delivery.removalFromStorage()),
                sdf.format(delivery.deliveryDate()),
                AddressResponse.fromAddress(delivery.address()));
    }
}
