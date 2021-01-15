package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.core.data.message.MessageRepository;
import foodwarehouse.database.rowmappers.MakerResultSetMapper;
import foodwarehouse.database.rowmappers.MessageResultSetMapper;
import foodwarehouse.database.tables.MakerTable;
import foodwarehouse.database.tables.MessageTable;
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
public class JdbcMessageRepository implements MessageRepository {

    private final Connection connection;

    @Autowired
    JdbcMessageRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<Message> createMessage(Employee sender, Employee receiver, String content) {
        try {
            CallableStatement callableStatement = connection.prepareCall(MessageTable.Procedures.INSERT);
            callableStatement.setInt(1, sender.employeeId());
            callableStatement.setInt(2, receiver.employeeId());
            callableStatement.setString(3, content);

            callableStatement.executeQuery();
            int messageId = callableStatement.getInt(4);
            return Optional.of(new Message(messageId, sender, receiver, content, "SENT", new Date(), null));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Message> updateMessageContent(int messageId, Employee sender, Employee receiver, String content) {
        try {
            CallableStatement callableStatement = connection.prepareCall(MessageTable.Procedures.UPDATE_CONTENT);
            callableStatement.setInt(1, messageId);
            callableStatement.setString(2, content);

            callableStatement.executeQuery();
            Date sendDate = callableStatement.getTimestamp(3);
            return Optional.of(new Message(messageId, sender, receiver, content, "SENT", sendDate, null));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void updateMessageRead(int messageId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(MessageTable.Procedures.UPDATE_READ);
            callableStatement.setInt(1, messageId);

            callableStatement.executeQuery();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public boolean deleteMessage(int messageId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(MessageTable.Procedures.DELETE);
            callableStatement.setInt(1, messageId);

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Message> findMessageById(int messageId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(MessageTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, messageId);

            ResultSet resultSet = callableStatement.executeQuery();
            Message message = null;
            if(resultSet.next()) {
                message = new MessageResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(message);
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAllMessages() {
        List<Message> messages = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(MessageTable.Procedures.READ_ALL);
            ResultSet resultSet = callableStatement.executeQuery();

            while(resultSet.next()) {
                messages.add(new MessageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            messages = null;
        }
        return messages;
    }

    @Override
    public List<Message> findEmployeeMessages(int employeeId) {
        List<Message> messages = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(MessageTable.Procedures.READ_EMPLOYEE_ALL);
            callableStatement.setInt(1, employeeId);
            ResultSet resultSet = callableStatement.executeQuery();

            while(resultSet.next()) {
                messages.add(new MessageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            messages = null;
        }
        return messages;
    }

    @Override
    public int countUnreadReceivedMessages(int employeeId) {
        int result = 0;
        try {
            CallableStatement callableStatement = connection.prepareCall(MessageTable.Procedures.COUNT_UNREAD_RECEIVED);
            callableStatement.setInt(1, employeeId);
            ResultSet resultSet = callableStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt("RESULT");
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return result;
    }
}
