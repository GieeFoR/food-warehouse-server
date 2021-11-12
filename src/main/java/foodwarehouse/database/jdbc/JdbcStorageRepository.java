package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.employee.Employee;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.core.data.storage.StorageRepository;
import foodwarehouse.database.rowmappers.EmployeeResultSetMapper;
import foodwarehouse.database.rowmappers.StorageResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.StorageTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStorageRepository implements StorageRepository {

//    private final Connection connection;
//
//    @Autowired
//    public JdbcStorageRepository(DataSource dataSource) {
//        try {
//            this.connection = dataSource.getConnection();
//        }
//        catch(SQLException sqlException) {
//            throw new RestException("Cannot connect to database!");
//        }
//    }

    @Override
    public Optional<Storage> insertStorage(
            Address address,
            Employee manager,
            String name,
            int capacity,
            boolean isColdStorage) {

        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("storage"), Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, address.addressId());
                statement.setInt(2, manager.employeeId());
                statement.setString(3, name);
                statement.setInt(4, capacity);
                statement.setBoolean(5, isColdStorage);

                statement.executeUpdate();
                statement.close();

                int storageId = statement.getGeneratedKeys().getInt(1);

                return Optional.of(new Storage(storageId, address, manager, name, capacity, isColdStorage));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
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
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("storage"));
                statement.setInt(1, manager.employeeId());
                statement.setString(2, name);
                statement.setInt(3, capacity);
                statement.setBoolean(4, isColdStorage);
                statement.setInt(5, storageId);

                statement.executeUpdate();
                statement.close();

                return Optional.of(new Storage(storageId, address, manager, name, capacity, isColdStorage));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteStorage(int storageId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("storage"));
                statement.setInt(1, storageId);

                statement.executeUpdate();
                statement.close();

                return true;
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Storage> findAllStorages() {
        List<Storage> storages = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("storage"));

                ResultSet resultSet = statement.executeQuery();

                while(resultSet.next()) {
                    storages.add(new StorageResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            storages = null;
        }
        return storages;
    }

    @Override
    public Optional<Storage> findStorage(int storageId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("storage_byId"));
                statement.setInt(1, storageId);

                ResultSet resultSet = statement.executeQuery();
                Storage storage = null;
                if(resultSet.next()) {
                    storage = new StorageResultSetMapper().resultSetMap(resultSet, "");
                }
                statement.close();

                return Optional.ofNullable(storage);
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Storage> findStorageByBatchId(int batchId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("storage_byBatchId"));
                statement.setInt(1, batchId);
                statement.setInt(2, batchId);

                ResultSet resultSet = statement.executeQuery();
                Storage storage = null;
                if(resultSet.next()) {
                    storage = new StorageResultSetMapper().resultSetMap(resultSet, "");
                }
                statement.close();

                return Optional.ofNullable(storage);
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Storage> findStoragesRunningOutOfSpace() {
        List<Storage> storages = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/GieeF/IdeaProjects/food-warehouse-server/test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("storage_runningOutOfSpace"));

                ResultSet resultSet = statement.executeQuery();

                while(resultSet.next()) {
                    storages.add(new StorageResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            storages = null;
        }
        return storages;
    }
}
