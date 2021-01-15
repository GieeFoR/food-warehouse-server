package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.core.data.message.MessageRepository;
import foodwarehouse.database.rowmappers.MessageResultSetMapper;
import foodwarehouse.database.tables.MessageTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return Optional.empty();
    }

    @Override
    public Optional<Message> updateMessageContent(int messageId, String content) {
        return Optional.empty();
    }

    @Override
    public Optional<Message> updateMessageRead(int messageId) {
        return Optional.empty();
    }

    @Override
    public boolean deleteMessage(int messageId) {
        return false;
    }

    @Override
    public Optional<Message> findMessageById(int messageId) {
        return Optional.empty();
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
