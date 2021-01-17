package foodwarehouse.web.response.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.web.response.customer.CustomerDataResponse;
import foodwarehouse.web.response.order.OrderDataResponse;
import foodwarehouse.web.response.order.OrderDeliveryResponse;
import foodwarehouse.web.response.payment.PaymentResponse;

public record ComplaintOrderResponse(
        @JsonProperty(value = "order", required = true)             OrderDataResponse orderDataResponse,
        @JsonProperty(value = "payment", required = true)           PaymentResponse paymentState,
        @JsonProperty(value = "delivery", required = true)          OrderDeliveryResponse orderDeliveryResponse,
        @JsonProperty(value = "customer", required = true)          CustomerDataResponse customerDataResponse) {

    public static ComplaintOrderResponse fromOrder(Order order) {
        return new ComplaintOrderResponse(
                OrderDataResponse.fromOrder(order),
                PaymentResponse.fromPayment(order.payment()),
                OrderDeliveryResponse.fromDelivery(order.delivery()),
                CustomerDataResponse.fromCustomer(order.customer()));
    }
}
