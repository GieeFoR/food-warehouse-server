package foodwarehouse.web.controller;

import foodwarehouse.core.data.order.Order;
import foodwarehouse.core.service.ComplaintService;
import foodwarehouse.core.service.ConnectionService;
import foodwarehouse.core.service.OrderService;
import foodwarehouse.web.common.SuccessResponse;
import foodwarehouse.web.error.DatabaseException;
import foodwarehouse.web.error.RestException;
import foodwarehouse.web.request.complaint.CreateComplaintRequest;
import foodwarehouse.web.response.complaint.ComplaintResponse;
import foodwarehouse.web.response.others.CancelResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/complaint")
public class ComplaintController {
    private final OrderService orderService;
    private final ComplaintService complaintService;
    private final ConnectionService connectionService;

    public ComplaintController(OrderService orderService, ComplaintService complaintService, ConnectionService connectionService) {
        this.orderService = orderService;
        this.complaintService = complaintService;
        this.connectionService = connectionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<ComplaintResponse> createComplaint(@RequestBody CreateComplaintRequest request) {
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
                .map(ComplaintResponse::fromComplaint)
                .map(SuccessResponse::new)
                .orElseThrow(() -> new RestException("Cannot create a complaint."));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Customer')")
    public SuccessResponse<CancelResponse> cancelComplaint(@PathVariable int id) {
        //check if database is reachable
        if(!connectionService.isReachable()) {
            String exceptionMessage = "Cannot connect to database.";
            System.out.println(exceptionMessage);
            throw new DatabaseException(exceptionMessage);
        }

        complaintService.findCustomerComplaints(id);

        return new SuccessResponse<>(new CancelResponse(true));
    }
}
