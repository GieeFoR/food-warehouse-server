package foodwarehouse.web.controller;

import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.PaymentTypeService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.paymentType.CreatePaymentTypeRequest;
import foodwarehouse.web.request.paymentType.UpdatePaymentTypeRequest;
import foodwarehouse.web.response.others.DeleteResponse;
import foodwarehouse.web.response.paymentType.PaymentTypeResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment-type")
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;
    private final ConnectionService connectionService;

    public PaymentTypeController(
            PaymentTypeService paymentTypeService,
            ConnectionService connectionService) {
        this.paymentTypeService = paymentTypeService;
        this.connectionService = connectionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<List<PaymentTypeResponse>> getPaymentTypes() {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        final var cars = paymentTypeService
                .findPaymentTypes()
                .stream()
                .map(PaymentTypeResponse::fromPaymentType)
                .collect(Collectors.toList());

        return new SuccessResponse<>(cars);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<PaymentTypeResponse> getPaymentTypeById(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return paymentTypeService
                .findPaymentTypeById(id)
                .map(PaymentTypeResponse::fromPaymentType)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot find payment type with this ID."));
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<PaymentTypeResponse> createPaymentType(@RequestBody CreatePaymentTypeRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return paymentTypeService
                .createPaymentType(
                        request.type())
                .map(PaymentTypeResponse::fromPaymentType)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a new payment type."));
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public SuccessResponse<PaymentTypeResponse> updatePaymentType(@RequestBody UpdatePaymentTypeRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        return paymentTypeService
                .updatePaymentType(
                        request.paymentTypeId(),
                        request.type())
                .map(PaymentTypeResponse::fromPaymentType)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot update payment type."));
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
                            paymentTypeService.deletePaymentType(i)));
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
                        paymentTypeService.deletePaymentType(id)));
    }
}
