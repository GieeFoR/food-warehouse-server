package foodwarehouse.database.jdbc;

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

@Repository
public class JdbcMessageRepository implements MessageRepository {

    private final Connection connection;

    @Autowired
    JdbcMessageRepository(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
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
