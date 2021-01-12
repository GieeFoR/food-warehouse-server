package foodwarehouse.core.data.complaint;

import foodwarehouse.core.data.order.Order;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository {

    Optional<Complaint> createComplaint(Order order, String content);

    Optional<Complaint> findComplaintById(int complaintId);

    List<Complaint> findComplaints();

    List<Complaint> findCustomerComplaints(int customerId);

    void cancelComplaint(int complaintId);

    void addDecisionToComplaint(int complaintId, String decision, ComplaintState complaintState);

    List<Complaint> findOrderComplaints(int orderId);
}
