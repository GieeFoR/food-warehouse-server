package foodwarehouse.core.data.message;

import foodwarehouse.core.data.employee.Employee;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    Optional<Message> createMessage(Employee sender, Employee receiver, String content);

    Optional<Message> updateMessageContent(int messageId, Employee sender, Employee receiver, String content, Date sendDate);

    void updateMessageRead(int messageId);

    boolean deleteMessage(int messageId);

    Optional<Message> findMessageById(int messageId);

    List<Message> findAllMessages();

    List<Message> findEmployeeMessages(int employeeId);

    int countUnreadReceivedMessages(int employeeId);
}
