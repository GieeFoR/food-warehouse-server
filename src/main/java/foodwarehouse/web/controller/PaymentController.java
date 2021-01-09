package foodwarehouse.web.controller;

import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentState;
import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.PaymentService;
import foodwarehouse.core.service.PaymentTypeService;
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

    public PaymentController(
            PaymentService paymentService,
            PaymentTypeService paymentTypeService,
            ConnectionService connectionService) {
        this.paymentService = paymentService;
        this.paymentTypeService = paymentTypeService;
        this.connectionService = connectionService;
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
    //@PreAuthorize("hasRole('Admin') || hasRole('Customer')")
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
    public SuccessResponse<PaymentResponse> getPaymentTypeById(@PathVariable int id) {
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
    public SuccessResponse<PaymentResponse> createPaymentType(@RequestBody CreatePaymentRequest request) {
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
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<PaymentResponse> acceptPayment(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
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
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<PaymentResponse> rejectPayment(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Payment rejectedPayment = paymentService.updatePaymentState(id, PaymentState.REJECTED)
                .orElseThrow(() -> new RestException("Cannot update a payment state."));

        Payment newPayment = paymentService.createPayment(new PaymentType(1, "Za pobraniem gotówką"), rejectedPayment.value())
                .orElseThrow(() -> new RestException("Cannot create a new payment"));

        return paymentService
                .updatePaymentState(
                        newPayment.paymentId(),
                        PaymentState.WAITING)
                .map(PaymentResponse::fromPayment)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update a payment state."));
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
