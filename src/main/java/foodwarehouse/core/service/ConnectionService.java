package foodwarehouse.core.service;

import foodwarehouse.database.jdbc.repos.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    private final ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public boolean isReachable() {
        return connectionRepository.isReachable();
    }
}
