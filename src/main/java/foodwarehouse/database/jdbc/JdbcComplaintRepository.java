package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.complaint.Complaint;
import foodwarehouse.core.data.complaint.ComplaintRepository;
import foodwarehouse.core.data.complaint.ComplaintState;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.database.rowmappers.ComplaintResultSetMapper;
import foodwarehouse.database.tables.ComplaintTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcComplaintRepository implements ComplaintRepository {

    private final Connection connection;

    @Autowired
    JdbcComplaintRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<Complaint> createComplaint(Order order, String content) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ComplaintTable.Procedures.INSERT);
            callableStatement.setInt(1, order.orderId());
            callableStatement.setString(2, content);

            callableStatement.executeQuery();
            int complaintId = callableStatement.getInt(3);
            return Optional.of(new Complaint(complaintId, order, content, new Date(), ComplaintState.REGISTERED, null, null));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Complaint> findComplaintById(int complaintId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ComplaintTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, complaintId);

            ResultSet resultSet = callableStatement.executeQuery();
            Complaint complaint = null;
            if(resultSet.next()) {
                complaint = new ComplaintResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(complaint);
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Complaint> findComplaints() {
        List<Complaint> complaints = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ComplaintTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                complaints.add(new ComplaintResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return complaints;
    }

    @Override
    public List<Complaint> findCustomerComplaints(int customerId) {
        List<Complaint> complaints = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ComplaintTable.Procedures.READ_CUSTOMER_COMPLAINTS);
            callableStatement.setInt(1, customerId);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                complaints.add(new ComplaintResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return complaints;
    }

    @Override
    public void cancelComplaint(int complaintId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ComplaintTable.Procedures.CANCEL);
            callableStatement.setInt(1, complaintId);

            callableStatement.executeQuery();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());

        }
    }

    @Override
    public void addDecisionToComplaint(int complaintId, String decision, ComplaintState complaintState) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ComplaintTable.Procedures.UPDATE_STATE);
            callableStatement.setInt(1, complaintId);
            callableStatement.setString(2, decision);
            callableStatement.setString(3, complaintState.value());

            callableStatement.executeQuery();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public List<Complaint> findOrderComplaints(int orderId) {
        List<Complaint> complaints = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ComplaintTable.Procedures.READ_BY_ORDER_ID);
            callableStatement.setInt(1, orderId);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                complaints.add(new ComplaintResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return complaints;
    }
}
