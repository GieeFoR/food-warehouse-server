package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.web.response.customer.CustomerDataResponse;
import foodwarehouse.web.response.payment.PaymentResponse;

import java.util.LinkedList;
import java.util.List;

public record SupplierOrderResponse(
        @JsonProperty(value = "order", required = true)         OrderDataResponse orderDataResponse,
        @JsonProperty(value = "products", required = true)      List<OrderProductResponse>orderProductsResponse,
        @JsonProperty(value = "delivery", required = true)      OrderDeliveryResponse orderDeliveryResponse,
        @JsonProperty(value = "payment", required = true)       PaymentResponse paymentResponse,
        @JsonProperty(value = "customer", required = true)      CustomerDataResponse customerDataResponse) {

    public static SupplierOrderResponse from(Order order, List<ProductBatch> products, List<Integer> quantity) {
        List<OrderProductResponse> orderProductResponses = new LinkedList<>();

        for(int i = 0; i < products.size(); i++) {
            orderProductResponses.add(OrderProductResponse.fromProductBatch(products.get(i), quantity.get(i)));
        }

        return new SupplierOrderResponse(
                OrderDataResponse.fromOrder(order),
                orderProductResponses,
                OrderDeliveryResponse.fromDelivery(order.delivery()),
                PaymentResponse.fromPayment(order.payment()),
                CustomerDataResponse.fromCustomer(order.customer()));
    }
}
