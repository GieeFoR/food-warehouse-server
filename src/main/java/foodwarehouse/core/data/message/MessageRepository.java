package foodwarehouse.core.data.message;

import java.sql.SQLException;
import java.util.List;

public interface MessageRepository {
    List<Message> findAllMessages() throws SQLException;
}
