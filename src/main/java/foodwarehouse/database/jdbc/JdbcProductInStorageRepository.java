package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.productInStorage.ProductInStorageRepository;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.database.rowmappers.ProductInStorageResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.ProductInStorageTable;
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
public class JdbcProductInStorageRepository implements ProductInStorageRepository {

    private final Connection connection;

    @Autowired
    JdbcProductInStorageRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<ProductInStorage> createProductInStorage(
            ProductBatch productBatch,
            Storage storage,
            int quantity) {

        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("productInStorage"), Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, productBatch.batchId());
            statement.setInt(2, storage.storageId());
            statement.setInt(3, quantity);

            statement.executeUpdate();
            return Optional.of(new ProductInStorage(productBatch, storage, quantity));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProductInStorage> updateProductInStorage(
            ProductBatch productBatch,
            Storage storage,
            int quantity) {

        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("productInStorage"));
            statement.setInt(1, quantity);
            statement.setInt(2, productBatch.batchId());
            statement.setInt(3, storage.storageId());

            statement.executeUpdate();
            return Optional.of(new ProductInStorage(productBatch, storage, quantity));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteProductInStorage(ProductBatch productBatch, Storage storage) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("productInStorage"));
            statement.setInt(1, productBatch.batchId());
            statement.setInt(2, storage.storageId());

            statement.executeUpdate();
            return true;
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return false;
        }
    }

    @Override
    public List<ProductInStorage> findProductInStorageAll() {
        List<ProductInStorage> productInStorages = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productInStorage"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                productInStorages.add(new ProductInStorageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            sqlException.getMessage();
        }
        return productInStorages;
    }

    @Override
    public List<ProductInStorage> findExpiredProductInStorage() {
        List<ProductInStorage> productInStorages = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productInStorage_expired"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                productInStorages.add(new ProductInStorageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            sqlException.getMessage();
        }
        return productInStorages;
    }

    @Override
    public Optional<ProductInStorage> findProductInStorageById(ProductBatch productBatch, Storage storage) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productInStorage_byId"));
            statement.setInt(1, productBatch.batchId());
            statement.setInt(2, storage.storageId());

            ResultSet resultSet = statement.executeQuery();
            ProductInStorage productInStorage = null;
            if(resultSet.next()) {
                productInStorage = new ProductInStorageResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(productInStorage);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<ProductInStorage> findProductInStorageAllByProductId(int productId) {
        List<ProductInStorage> productInStorages = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productInStorage_allByProductId"));
            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                productInStorages.add(new ProductInStorageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return productInStorages;
    }

    @Override
    public List<ProductInStorage> findProductInStorageAllByBatchId(int batchId) {
        List<ProductInStorage> productInStorages = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productInStorage_allByBatchId"));
            statement.setInt(1, batchId);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                productInStorages.add(new ProductInStorageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return productInStorages;
    }

    @Override
    public float findProductPrice(int batchId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productInStorage_productPrice"));
            statement.setInt(1, batchId);
            statement.setInt(2, batchId);
            statement.setInt(3, batchId);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getFloat("RESULT");
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }
}
