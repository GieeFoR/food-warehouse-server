package foodwarehouse.core.service;

import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.message.Message;
import foodwarehouse.core.data.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Optional<Message> createMessage(Employee sender, Employee receiver, String content) {
        return messageRepository.createMessage(sender, receiver, content);
    }

    public Optional<Message> updateMessageContent(int messageId, Employee sender, Employee receiver, String content) {
        return messageRepository.updateMessageContent(messageId, sender, receiver, content);
    }

    public void updateMessageRead(int messageId) {
        messageRepository.updateMessageRead(messageId);
    }

    public boolean deleteMessage(int messageId) {
        return messageRepository.deleteMessage(messageId);
    }

    public Optional<Message> findMessageById(int messageId) {
        return messageRepository.findMessageById(messageId);
    }

    public List<Message> findAllMessages() {
        return messageRepository.findAllMessages();
    }

    public List<Message> findEmployeeMessages(int employeeId) {
        return messageRepository.findEmployeeMessages(employeeId);
    }

    public int countUnreadReceivedMessages(int employeeId) {
        return messageRepository.countUnreadReceivedMessages(employeeId);
    }
}
