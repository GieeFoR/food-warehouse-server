package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.web.response.payment.PaymentResponse;

import java.util.List;
import java.util.stream.Collectors;

public record OrderResponse(
        @JsonProperty(value = "order", required = true)         OrderDataResponse orderDataResponse,
        @JsonProperty(value ="products", required = true)       List<OrderProductResponse> orderProductsResponse,
        @JsonProperty(value = "payment", required = true)       PaymentResponse paymentState,
        @JsonProperty(value = "delivery", required = true)      OrderDeliveryResponse orderDeliveryResponse) {

        public static OrderResponse from(Order order, List<ProductBatch> products) {
                return new OrderResponse(
                        OrderDataResponse.fromOrder(order),
                        products.stream().map(OrderProductResponse::fromProductBatch).collect(Collectors.toList()),
                        PaymentResponse.fromPayment(order.payment()),
                        OrderDeliveryResponse.fromDelivery(order.delivery()));
        }
}
