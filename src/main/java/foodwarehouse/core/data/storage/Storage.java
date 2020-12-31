package foodwarehouse.core.data.storage;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;

public record Storage(
        int storageId,
        Address address,
        Employee manager,
        String storageName,
        int capacity,
        boolean isColdStorage) {
}
