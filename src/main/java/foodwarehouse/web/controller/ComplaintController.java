package foodwarehouse.web.controller;

import foodwarehouse.core.data.customer.Customer;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.service.ComplaintService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.CustomerService;
import foodwarehouse.core.service.OrderService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.complaint.CreateComplaintRequest;
import foodwarehouse.web.response.complaint.ComplaintResponse;
import foodwarehouse.web.response.complaint.CustomerComplaintResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ComplaintController {
    private final OrderService orderService;
    private final ComplaintService complaintService;
    private final CustomerService customerService;
    private final ConnectionService connectionService;

    public ComplaintController(
            OrderService orderService,
            ComplaintService complaintService,
            CustomerService customerService,
            ConnectionService connectionService) {
        this.orderService = orderService;
        this.complaintService = complaintService;
        this.customerService = customerService;
        this.connectionService = connectionService;
    }

    @PostMapping("/order/complaint")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<CustomerComplaintResponse> createComplaint(@RequestBody CreateComplaintRequest request) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Order order = orderService.findOrderById(request.orderId())
                .orElseThrow(() -> new RestException("Cannot find order."));

        return complaintService
                .createComplaint(order, request.content())
                .map(CustomerComplaintResponse::fromComplaint)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a complaint."));
    }

    @PutMapping("/order/complaint/{id}")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<Void> cancelComplaint(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        complaintService.cancelComplaint(id);
        return new SuccessResponse<>(null);
    }

    @GetMapping("/order/complaint")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<List<CustomerComplaintResponse>> getCustomerComplaints(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Customer customer = customerService
                .findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find customer."));

        final var result = complaintService
                .findCustomerComplaints(customer.customerId())
                .stream()
                .map(CustomerComplaintResponse::fromComplaint)
                .collect(Collectors.toList());

        return new SuccessResponse<>(result);
    }

    @GetMapping("/complaint")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<List<ComplaintResponse>> getComplaints(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Customer customer = customerService
                .findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find customer."));

        final var result = complaintService
                .findCustomerComplaints(customer.customerId())
                .stream()
                .map(ComplaintResponse::fromComplaint)
                .collect(Collectors.toList());

        return new SuccessResponse<>(result);
    }

    @PostMapping("/complaint")
    @PreAuthorize("hasRole('Admin') || hasRole('Manager')")
    public SuccessResponse<List<ComplaintResponse>> makeDecisionComplaints(Authentication authentication) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        Customer customer = customerService
                .findCustomerByUsername(authentication.getName())
                .orElseThrow(() -> new RestException("Cannot find customer."));

        final var result = complaintService
                .findCustomerComplaints(customer.customerId())
                .stream()
                .map(ComplaintResponse::fromComplaint)
                .collect(Collectors.toList());

        return new SuccessResponse<>(result);
    }
}
