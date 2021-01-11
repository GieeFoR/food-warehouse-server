package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.order.Order;

public record OrderDataResponse(
        @JsonProperty(value = "order_id", required = true)          int orderId,
        @JsonProperty(value = "order_state", required = true)      String orderState,
        @JsonProperty(value = "comment", required = true)           String comment) {

    public static OrderDataResponse fromOrder(Order order) {
        return new OrderDataResponse(order.orderId(), order.state().value(), order.comment());
    }
}
