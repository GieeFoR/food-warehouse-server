package foodwarehouse.core.data.storage;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StorageRepository {

    Optional<Storage> insertStorage(Address address, Employee manager, String name, int capacity, boolean isColdStorage);

    Optional<Storage> updateStorage(int storageId, Address address, Employee manager, String name, int capacity, boolean isColdStorage);

    boolean deleteStorage(int storageId);

    List<Storage> findAllStorages();

    Optional<Storage> findStorage(int storageId);

    Optional<Storage> findStorageByBatchId(int batchId);
}
