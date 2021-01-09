package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productBatch.ProductBatchRepository;
import foodwarehouse.database.rowmappers.ProductBatchResultSetMapper;
import foodwarehouse.database.rowmappers.ProductResultSetMapper;
import foodwarehouse.database.tables.ProductBatchTable;
import foodwarehouse.database.tables.ProductTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductBatchRepository implements ProductBatchRepository {

    private final Connection connection;

    @Autowired
    JdbcProductBatchRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<ProductBatch> createProductBatch(
            Product product,
            int batchNo,
            Date eatByDate,
            int quantity) {

        try {
            CallableStatement callableStatement = connection.prepareCall(ProductBatchTable.Procedures.INSERT);
            callableStatement.setInt(1, product.productId());
            callableStatement.setInt(2, batchNo);
            callableStatement.setDate(3, new java.sql.Date(eatByDate.getTime()));
            callableStatement.setInt(4, quantity);

            callableStatement.executeQuery();
            int productBatchId = callableStatement.getInt(5);
            return Optional.of(new ProductBatch(productBatchId, product, batchNo, eatByDate, 0, quantity));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProductBatch> updateProductBatch(
            int productBatchId,
            Product product,
            int batchNo,
            Date eatByDate,
            int discount,
            int quantity) {

        try {
            CallableStatement callableStatement = connection.prepareCall(ProductBatchTable.Procedures.UPDATE);
            callableStatement.setInt(1, productBatchId);
            callableStatement.setInt(2, batchNo);
            callableStatement.setInt(3, discount);
            callableStatement.setDate(4, new java.sql.Date(eatByDate.getTime()));
            callableStatement.setInt(5, quantity);

            callableStatement.executeQuery();
            return Optional.of(new ProductBatch(productBatchId, product, batchNo, eatByDate, discount, quantity));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteProductBatch(int productBatchId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductBatchTable.Procedures.DELETE);
            callableStatement.setInt(1, productBatchId);

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<ProductBatch> findProductBatchById(int productBatchId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductBatchTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, productBatchId);

            ResultSet resultSet = callableStatement.executeQuery();
            ProductBatch productBatch = null;
            if(resultSet.next()) {
                productBatch = new ProductBatchResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(productBatch);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<ProductBatch> findProductBatches() {
        List<ProductBatch> productBatches = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductBatchTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                productBatches.add(new ProductBatchResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return productBatches;
    }
}
