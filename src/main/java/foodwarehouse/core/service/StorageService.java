package foodwarehouse.core.service;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.core.data.storage.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {
    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Optional<Storage> insertStorage(Address address, Employee manager, String name, int capacity, boolean isColdStorage) throws SQLException {
        return storageRepository.insertStorage(address, manager, name, capacity, isColdStorage);
    }

    public Optional<Storage> updateStorage(int storageId, Address address, Employee manager, String name, int capacity, boolean isColdStorage) throws SQLException {
        return storageRepository.updateStorage(storageId, address, manager, name, capacity, isColdStorage);
    }

    public boolean deleteStorage(int storageId) throws SQLException {
        return storageRepository.deleteStorage(storageId);
    }

    public List<Storage> findAllStorages() throws SQLException {
        return storageRepository.findAllStorages();
    }

    public Optional<Storage> findStorage(int storageId) throws SQLException {
        return storageRepository.findStorage(storageId);
    }
}
