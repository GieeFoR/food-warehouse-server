package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.core.data.message.MessageRepository;
import foodwarehouse.database.rowmappers.MakerResultSetMapper;
import foodwarehouse.database.rowmappers.MessageResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.MakerTable;
import foodwarehouse.database.tables.MessageTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMessageRepository implements MessageRepository {

//    private final Connection connection;
//
//    @Autowired
//    JdbcMessageRepository(DataSource dataSource) {
//        try {
//            this.connection = dataSource.getConnection();
//        }
//        catch(SQLException sqlException) {
//            throw new RestException("Cannot connect to database!");
//        }
//    }

    @Override
    public Optional<Message> createMessage(Employee sender, Employee receiver, String content) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("message"), Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, sender.employeeId());
                statement.setInt(2, receiver.employeeId());
                statement.setString(3, content);

                statement.executeUpdate();
                int messageId = statement.getGeneratedKeys().getInt(1);
                statement.close();

                return Optional.of(new Message(messageId, sender, receiver, content, "SENT", new Date(), null));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Message> updateMessageContent(int messageId, Employee sender, Employee receiver, String content, Date sendDate) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("message"));
                statement.setString(1, content);
                statement.setInt(2, messageId);

                statement.executeUpdate();
                statement.close();

                return Optional.of(new Message(messageId, sender, receiver, content, "SENT", sendDate, null));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void updateMessageRead(int messageId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("message_read"));
                statement.setInt(1, messageId);

                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean deleteMessage(int messageId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("message"));
                statement.setInt(1, messageId);

                statement.executeUpdate();
                statement.close();

                return true;
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Message> findMessageById(int messageId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("message_byId"));
                statement.setInt(1, messageId);

                ResultSet resultSet = statement.executeQuery();
                Message message = null;
                if(resultSet.next()) {
                    message = new MessageResultSetMapper().resultSetMap(resultSet, "");
                }
                statement.close();

                return Optional.ofNullable(message);
            }
        } catch (SQLException | FileNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAllMessages() {
        List<Message> messages = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("message"));
                ResultSet resultSet = statement.executeQuery();

                while(resultSet.next()) {
                    messages.add(new MessageResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            messages = null;
        }
        return messages;
    }

    @Override
    public List<Message> findEmployeeMessages(int employeeId) {
        List<Message> messages = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("message_byEmployeeId"));
                statement.setInt(1, employeeId);
                statement.setInt(2, employeeId);
                ResultSet resultSet = statement.executeQuery();

                while(resultSet.next()) {
                    messages.add(new MessageResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            messages = null;
        }
        return messages;
    }

    @Override
    public int countUnreadReceivedMessages(int employeeId) {
        int result = 0;
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("message_countEmployeeUnread"));
                statement.setInt(1, employeeId);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()) {
                    return resultSet.getInt("RESULT");
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
