package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.order.Order;

import java.text.SimpleDateFormat;

public record OrderDataResponse(
        @JsonProperty(value = "order_id", required = true)          int orderId,
        @JsonProperty(value = "order_state", required = true)      String orderState,
        @JsonProperty(value = "comment", required = true)           String comment,
        @JsonProperty(value = "date", required = true)              String orderDate) {

    public static OrderDataResponse fromOrder(Order order) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new OrderDataResponse(order.orderId(), order.state().value(), order.comment(), sdf.format(order.orderDate()));
    }
}
