package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productInStorage.ProductInStorage;
import foodwarehouse.core.data.productInStorage.ProductInStorageRepository;
import foodwarehouse.core.data.storage.Storage;
import foodwarehouse.database.tables.ProductBatchTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
    public Optional<ProductInStorage> createProductInStorage(ProductBatch productBatch, Storage storage, int quantity) {
//        try {
//            CallableStatement callableStatement = connection.prepareCall(ProductBatchTable.Procedures.INSERT);
//            callableStatement.setInt(1, product.productId());
//            callableStatement.setInt(2, batchNo);
//            callableStatement.setDate(3, new java.sql.Date(eatByDate.getTime()));
//            callableStatement.setInt(4, quantity);
//
//            callableStatement.executeQuery();
//            int productBatchId = callableStatement.getInt(5);
//            return Optional.of(new ProductBatch(productBatchId, product, batchNo, eatByDate, 0, quantity));
//        }
//        catch (SQLException sqlException) {
//            System.out.println(sqlException.getMessage());
//            return Optional.empty();
//        }
        return null;
    }
}
