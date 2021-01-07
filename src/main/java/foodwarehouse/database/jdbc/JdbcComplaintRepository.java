package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.complaint.Complaint;
import foodwarehouse.core.data.complaint.ComplaintRepository;
import foodwarehouse.core.data.order.Order;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcComplaintRepository implements ComplaintRepository {
    @Override
    public Optional<Complaint> createComplaint(Order order, String content, Date sendDate, String state, String decision, Date decisionDate) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Complaint> findComplaintById(int complaintId) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Complaint> findComplaints() throws SQLException {
        return null;
    }
}
