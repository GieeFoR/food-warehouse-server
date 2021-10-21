package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.complaint.Complaint;
import foodwarehouse.core.data.complaint.ComplaintRepository;
import foodwarehouse.core.data.complaint.ComplaintState;
import foodwarehouse.core.data.order.Order;
import foodwarehouse.database.rowmappers.ComplaintResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.ComplaintTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.*;
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
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("complaint"), Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.orderId());
            statement.setString(2, content);
            statement.executeUpdate();

            int complaintId = statement.getGeneratedKeys().getInt(1);
            return Optional.of(new Complaint(complaintId, order, content, new Date(), ComplaintState.REGISTERED, null, null));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Complaint> findComplaintById(int complaintId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("complaint_byId"));
            statement.setInt(1, complaintId);

            ResultSet resultSet = statement.executeQuery();
            Complaint complaint = null;
            if(resultSet.next()) {
                complaint = new ComplaintResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(complaint);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Complaint> findComplaints() {
        List<Complaint> complaints = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("complaint"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                complaints.add(new ComplaintResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return complaints;
    }

    @Override
    public List<Complaint> findCustomerComplaints(int customerId) {
        List<Complaint> complaints = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("complaint_byCustomerId"));
            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                complaints.add(new ComplaintResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return complaints;
    }

    @Override
    public void cancelComplaint(int complaintId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("complaint_cancel"));
            statement.setInt(1, complaintId);
            statement.setInt(2, complaintId);

            statement.executeUpdate();
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());

        }
    }

    @Override
    public void addDecisionToComplaint(int complaintId, String decision, ComplaintState complaintState) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("complaint_decision"));
            statement.setInt(1, complaintId);
            statement.setString(2, decision);
            statement.setString(3, complaintState.value());
            statement.setInt(4, complaintId);

            statement.executeUpdate();
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public List<Complaint> findOrderComplaints(int orderId) {
        List<Complaint> complaints = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("complaint_byOrderId"));
            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                complaints.add(new ComplaintResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return complaints;
    }
}
