package foodwarehouse.web.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.web.response.employee.EmployeeMessageResponse;

import java.util.LinkedList;
import java.util.List;

public record EmployeeOrderResponse(
        @JsonProperty(value = "order", required = true)         OrderDataResponse orderDataResponse,
        @JsonProperty(value = "products", required = true)      List<OrderProductResponse>orderProductsResponse,
        @JsonProperty(value = "supplier", required = true)      EmployeeMessageResponse orderEmployeeResponse) {

    public static EmployeeOrderResponse from(Order order, List<ProductBatch> products, List<Integer> quantity) {
        List<OrderProductResponse> orderProductResponses = new LinkedList<>();

        for(int i = 0; i < products.size(); i++) {
            orderProductResponses.add(OrderProductResponse.fromProductBatch(products.get(i), quantity.get(i)));
        }

        return new EmployeeOrderResponse(
                OrderDataResponse.fromOrder(order),
                orderProductResponses,
                EmployeeMessageResponse.fromEmployee(order.delivery().supplier()));
    }
}
