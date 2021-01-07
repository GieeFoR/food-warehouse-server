package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.core.data.message.MessageRepository;
import foodwarehouse.database.rowmappers.MessageResultSetMapper;
import foodwarehouse.database.tables.MessageTable;
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
    JdbcMessageRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    @Override
    public Optional<Message> createMessage(Employee sender, Employee receiver, String content) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Message> updateMessageContent(int messageId, String content) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Message> updateMessageRead(int messageId) throws SQLException {
        return Optional.empty();
    }

    @Override
    public boolean deleteMessage(int messageId) throws SQLException {
        return false;
    }

    @Override
    public Optional<Message> findMessageById(int messageId) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Message> findAllMessages() throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(MessageTable.Procedures.READ_ALL);
        ResultSet resultSet = callableStatement.executeQuery();
        List<Message> messages = new LinkedList<>();
        while(resultSet.next()) {
            messages.add(new MessageResultSetMapper().resultSetMap(resultSet, ""));
        }
        return messages;
    }
}
