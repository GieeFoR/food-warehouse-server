package foodwarehouse.core.data.complaint;

import foodwarehouse.core.data.order.Order;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ComplaintRepository {

    Optional<Complaint> createComplaint(Order order, String content, Date sendDate, String state, String decision, Date decisionDate);

    Optional<Complaint> findComplaintById(int complaintId);

    List<Complaint> findComplaints();
}
