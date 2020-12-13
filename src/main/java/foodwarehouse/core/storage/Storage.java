package foodwarehouse.core.storage;

import foodwarehouse.core.user.employee.Employee;
import universitymanagement.core.common.Address;

public record Storage(
        int storageId,
        Address address,
        Employee manager,
        String storageName,
        int capacity,
        boolean isColdStorage) {
}
