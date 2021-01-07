package foodwarehouse.core.data.message;

import foodwarehouse.core.data.employee.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    Optional<Message> createMessage(Employee sender, Employee receiver, String content) throws SQLException;

    Optional<Message> updateMessageContent(int messageId, String content) throws SQLException;

    Optional<Message> updateMessageRead(int messageId) throws SQLException;

    boolean deleteMessage(int messageId) throws SQLException;

    Optional<Message> findMessageById(int messageId) throws SQLException;

    List<Message> findAllMessages() throws SQLException;
}
