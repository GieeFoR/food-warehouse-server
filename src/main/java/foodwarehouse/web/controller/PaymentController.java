package foodwarehouse.web.controller;

import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.data.order.OrderState;
import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentState;
import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.core.service.*;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.payment.CreatePaymentRequest;
import foodwarehouse.web.request.payment.UpdatePaymentValueRequest;
import foodwarehouse.web.request.paymentType.CreatePaymentTypeRequest;
import foodwarehouse.web.request.paymentType.UpdatePaymentTypeRequest;
import foodwarehouse.web.response.others.DeleteResponse;
import foodwarehouse.web.response.payment.PaymentResponse;
import foodwarehouse.web.response.paymentType.PaymentTypeResponse;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentTypeService paymentTypeService;
    private final ConnectionService connectionService;
    private final OrderService orderService;
    private final CustomerService customerService;

    public PaymentController(PaymentService paymentService, PaymentTypeService paymentTypeService, ConnectionService connectionService, OrderService orderService, CustomerService customerService) {
        this.paymentService = paymentService;
        this.paymentTypeService = paymentTypeService;
        this.connectionService = connectionService;
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<PaymentResponse>> getAllPayments() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var payments = paymentService
                .findPayments()
                .stream()
                .map(PaymentResponse::fromPayment)
                .collect(Collectors.toList());

        return new SuccessResponse<>(payments);
    }


    @GetMapping("/customer")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<List<PaymentResponse>> getCustomerPayments(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var payments = paymentService
                .findCustomerPayments(authentication.getName())
                .stream()
                .map(PaymentResponse::fromPayment)
                .collect(Collectors.toList());

        return new SuccessResponse<>(payments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<PaymentResponse> getPaymentById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return paymentService
                .findPaymentById(id)
                .map(PaymentResponse::fromPayment)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find payment with this ID."));
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<PaymentResponse> createPayment(@RequestBody CreatePaymentRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        PaymentType paymentType = paymentTypeService
                .findPaymentTypeById(request.paymentType())
                .orElseThrow(() -> new RestException("Cannot find payment type with this ID."));

        return paymentService
                .createPayment(
                        paymentType,
                        request.value())
                .map(PaymentResponse::fromPayment)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a new payment."));
    }

    @PutMapping("/value")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<PaymentResponse> updatePaymentValue(@RequestBody UpdatePaymentValueRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return paymentService
                .updatePaymentValue(
                        request.paymentId(),
                        request.value())
                .map(PaymentResponse::fromPayment)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update payment value."));
    }

    @PutMapping("/accept/{id}")
    @PreAuthorize("hasRole('Customer') || hasRole('Supplier')")
    public SuccessResponse<PaymentResponse> acceptPayment(Authentication authentication, @PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService
                .findOrders()
                .stream()
                .filter(o -> o.payment().paymentId() == id)
                .findFirst()
                .orElseThrow(() -> new RestException("Cannot find order with this payment."));

        if(authentication.getPrincipal().equals("Customer")) {
            Customer customer = customerService.findCustomerByUsername(authentication.getName())
                    .orElseThrow(() -> new RestException("Cannot find customer"));

            if(order.customer().customerId() != customer.customerId()) {
                throw new RestException("Cannot accept this payment.");
            }

            orderService.updateOrderState(
                    order.orderId(),
                    order.payment(),
                    order.customer(),
                    order.delivery(),
                    order.comment(),
                    OrderState.REGISTERED);
        }
        else {
            orderService.updateOrderState(
                    order.orderId(),
                    order.payment(),
                    order.customer(),
                    order.delivery(),
                    order.comment(),
                    OrderState.DELIVERED);
        }



        return paymentService
                .updatePaymentState(
                        id,
                        PaymentState.COMPLETED)
                .map(PaymentResponse::fromPayment)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update a payment state."));
    }

    @PutMapping("/reject/{id}")
    @PreAuthorize("hasRole('Customer') || hasRole('Supplier')")
    public SuccessResponse<PaymentResponse> rejectPayment(Authentication authentication, @PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService
                .findOrders()
                .stream()
                .filter(o -> o.payment().paymentId() == id)
                .findFirst()
                .orElseThrow(() -> new RestException("Cannot find order with this payment."));

        if(authentication.getPrincipal().equals("Customer")) {
            Customer customer = customerService.findCustomerByUsername(authentication.getName())
                    .orElseThrow(() -> new RestException("Cannot find customer"));

            if(order.customer().customerId() != customer.customerId()) {
                throw new RestException("Cannot accept this payment.");
            }
        }

        Payment rejectedPayment = paymentService.updatePaymentState(id, PaymentState.REJECTED)
                .orElseThrow(() -> new RestException("Cannot update a payment state."));

        if(authentication.getPrincipal().equals("Customer")) {
            Payment newPayment = paymentService.createPayment(new PaymentType(1, "Za pobraniem gotówką"), rejectedPayment.value())
                    .orElseThrow(() -> new RestException("Cannot create a new payment"));

            orderService.updateOrderState(
                    order.orderId(),
                    order.payment(),
                    order.customer(),
                    order.delivery(),
                    order.comment(),
                    OrderState.REGISTERED);

            return paymentService
                    .updatePaymentState(
                            newPayment.paymentId(),
                            PaymentState.WAITING)
                    .map(PaymentResponse::fromPayment)
                    .map(SuccessResponse::new)
                    .orElseThrow(() -> new RestException("Cannot update a payment state."));
        }
        else {
            orderService.updateOrderState(
                    order.orderId(),
                    order.payment(),
                    order.customer(),
                    order.delivery(),
                    order.comment(),
                    OrderState.RETURNED);

            return new SuccessResponse<>(PaymentResponse.fromPayment(rejectedPayment));
        }
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<PaymentResponse> cancelPayment(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return paymentService
                .updatePaymentState(
                        id,
                        PaymentState.CANCELED)
                .map(PaymentResponse::fromPayment)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update a payment state."));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<DeleteResponse>> deletePaymentTypes(@RequestBody List<Integer> request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        List<DeleteResponse> result = new LinkedList<>();
        for(int i : request) {
            result.add(
                    new DeleteResponse(
                            paymentService.deletePayment(i)));
        }

        return new SuccessResponse<>(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<DeleteResponse> deletePaymentTypeById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return new SuccessResponse<>(
                new DeleteResponse(
                        paymentService.deletePayment(id)));
    }
}
