package foodwarehouse.core.service;

import foodwarehouse.core.data.message.Message;
import foodwarehouse.core.data.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MessageService implements MessageRepository{
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findAllMessages() throws SQLException {
        return messageRepository.findAllMessages();
    }
}
