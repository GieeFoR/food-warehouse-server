package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.productInStorage.ProductInStorageRepository;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.database.rowmappers.ProductInStorageResultSetMapper;
import foodwarehouse.database.tables.ProductInStorageTable;
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
            CallableStatement callableStatement = connection.prepareCall(ProductInStorageTable.Procedures.INSERT);
            callableStatement.setInt(1, productBatch.batchId());
            callableStatement.setInt(2, storage.storageId());
            callableStatement.setInt(3, quantity);

            callableStatement.executeQuery();
            return Optional.of(new ProductInStorage(productBatch, storage, quantity));
        }
        catch (SQLException sqlException) {
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
            CallableStatement callableStatement = connection.prepareCall(ProductInStorageTable.Procedures.UPDATE);
            callableStatement.setInt(1, productBatch.batchId());
            callableStatement.setInt(2, storage.storageId());
            callableStatement.setInt(3, quantity);

            callableStatement.executeQuery();
            return Optional.of(new ProductInStorage(productBatch, storage, quantity));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteProductInStorage(ProductBatch productBatch, Storage storage) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductInStorageTable.Procedures.DELETE);
            callableStatement.setInt(1, productBatch.batchId());
            callableStatement.setInt(2, storage.storageId());

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public List<ProductInStorage> findProductInStorageAll() {
        List<ProductInStorage> productInStorages = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductInStorageTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                productInStorages.add(new ProductInStorageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return productInStorages;
    }

    @Override
    public List<ProductInStorage> findExpiredProductInStorage() {
        List<ProductInStorage> productInStorages = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductInStorageTable.Procedures.READ_EXPIRED_BATCHES);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                productInStorages.add(new ProductInStorageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return productInStorages;
    }

    @Override
    public Optional<ProductInStorage> findProductInStorageById(ProductBatch productBatch, Storage storage) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductInStorageTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, productBatch.batchId());
            callableStatement.setInt(2, storage.storageId());

            ResultSet resultSet = callableStatement.executeQuery();
            ProductInStorage productInStorage = null;
            if(resultSet.next()) {
                productInStorage = new ProductInStorageResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(productInStorage);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<ProductInStorage> findProductInStorageAllByProductId(int productId) {
        List<ProductInStorage> productInStorages = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductInStorageTable.Procedures.READ_BY_PRODUCT_ID);
            callableStatement.setInt(1, productId);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                productInStorages.add(new ProductInStorageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return productInStorages;
    }

    @Override
    public List<ProductInStorage> findProductInStorageAllByBatchId(int batchId) {
        List<ProductInStorage> productInStorages = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductInStorageTable.Procedures.READ_BY_BATCH_ID);
            callableStatement.setInt(1, batchId);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                productInStorages.add(new ProductInStorageResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return productInStorages;
    }

    @Override
    public float findProductPrice(int batchId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductInStorageTable.Procedures.READ_PRODUCT_PRICE);
            callableStatement.setInt(1, batchId);

            ResultSet resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getFloat("RESULT");
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }
}
