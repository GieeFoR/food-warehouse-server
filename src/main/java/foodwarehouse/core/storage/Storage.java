package foodwarehouse.core.storage;

import foodwarehouse.core.address.Address;
import foodwarehouse.core.user.employee.Employee;

public record Storage(
        int storageId,
        Address address,
        Employee manager,
        String storageName,
        int capacity,
        boolean isColdStorage) {
}
