package foodwarehouse.core.service;

import foodwarehouse.core.data.complaint.Complaint;
import foodwarehouse.core.data.complaint.ComplaintRepository;
import foodwarehouse.core.data.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService implements ComplaintRepository {

    private final ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    public Optional<Complaint> createComplaint(Order order, String content, Date sendDate, String state, String decision, Date decisionDate) throws SQLException {
        return complaintRepository.createComplaint(order, content, sendDate, state, decision, decisionDate);
    }

    @Override
    public Optional<Complaint> findComplaintById(int complaintId) throws SQLException {
        return complaintRepository.findComplaintById(complaintId);
    }

    @Override
    public List<Complaint> findComplaints() throws SQLException {
        return complaintRepository.findComplaints();
    }
}
