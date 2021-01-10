package foodwarehouse.web.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.web.request.address.CreateAddressRequest;

import java.util.List;

public record CreateOrderRequest(
        @JsonProperty(value = "products", required = true)          List<ProductInOrderData>products,
        @JsonProperty(value = "address")                            CreateAddressRequest newDeliveryAddress,
        @JsonProperty(value = "is_new_address", required = true)    boolean isNewAddress,
        @JsonProperty(value = "address_id")                         int existingDeliveryAddressId,
        @JsonProperty(value = "payment_type_id", required = true)   int paymentTypeId,
        @JsonProperty(value = "comment", required = true)           String comment) {
}
