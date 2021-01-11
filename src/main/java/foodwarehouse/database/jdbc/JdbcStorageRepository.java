package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.core.data.storage.StorageRepository;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.rowmappers.StorageResultSetMapper;
import foodwarehouse.database.tables.StorageTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStorageRepository implements StorageRepository {

    private final Connection connection;

    @Autowired
    public JdbcStorageRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<Storage> insertStorage(
            Address address,
            Employee manager,
            String name,
            int capacity,
            boolean isColdStorage) {

        try {
            CallableStatement callableStatement = connection.prepareCall(StorageTable.Procedures.INSERT);
            callableStatement.setInt(1, address.addressId());
            callableStatement.setInt(2, manager.employeeId());
            callableStatement.setString(3, name);
            callableStatement.setInt(4, capacity);
            callableStatement.setBoolean(5, isColdStorage);

            callableStatement.executeQuery();
            int storageId = callableStatement.getInt(6);

            return Optional.of(new Storage(storageId, address, manager, name, capacity, isColdStorage));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Storage> updateStorage(
            int storageId,
            Address address,
            Employee manager,
            String name,
            int capacity,
            boolean isColdStorage) {

        try {
            CallableStatement callableStatement = connection.prepareCall(StorageTable.Procedures.UPDATE);
            callableStatement.setInt(1, storageId);
            callableStatement.setInt(2, manager.employeeId());
            callableStatement.setString(3, name);
            callableStatement.setInt(4, capacity);
            callableStatement.setBoolean(5, isColdStorage);

            callableStatement.executeQuery();
            return Optional.of(new Storage(storageId, address, manager, name, capacity, isColdStorage));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteStorage(int storageId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(StorageTable.Procedures.DELETE);
            callableStatement.setInt(1, storageId);

            callableStatement.executeQuery();

            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public List<Storage> findAllStorages() {
        List<Storage> storages = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(StorageTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();

            while(resultSet.next()) {
                storages.add(new StorageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch (SQLException sqlException) {
            storages = null;
        }

        return storages;
    }

    @Override
    public Optional<Storage> findStorage(int storageId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(StorageTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, storageId);

            ResultSet resultSet = callableStatement.executeQuery();
            Storage storage = null;
            if(resultSet.next()) {
                storage = new StorageResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(storage);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Storage> findStorageByBatchId(int batchId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(StorageTable.Procedures.READ_BY_BATCH_ID);
            callableStatement.setInt(1, batchId);

            ResultSet resultSet = callableStatement.executeQuery();
            Storage storage = null;
            if(resultSet.next()) {
                storage = new StorageResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(storage);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }
}
