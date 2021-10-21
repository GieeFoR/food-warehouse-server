package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.productBatch.ProductBatch;
import foodwarehouse.core.data.productBatch.ProductBatchRepository;
import foodwarehouse.database.rowmappers.ProductBatchResultSetMapper;
import foodwarehouse.database.rowmappers.ProductResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.ProductBatchTable;
import foodwarehouse.database.tables.ProductTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.*;
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
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("productBatch"), Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, product.productId());
            statement.setInt(2, batchNo);
            statement.setDate(3, new java.sql.Date(eatByDate.getTime()));
            statement.setInt(4, quantity);

            statement.executeUpdate();
            int productBatchId = statement.getGeneratedKeys().getInt(1);
            return Optional.of(new ProductBatch(productBatchId, product, batchNo, eatByDate, 0, quantity));
        }
        catch (SQLException | FileNotFoundException sqlException) {
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
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("productBatch"));
            statement.setInt(1, batchNo);
            statement.setInt(2, discount);
            statement.setDate(3, new java.sql.Date(eatByDate.getTime()));
            statement.setInt(4, quantity);
            statement.setInt(5, productBatchId);


            statement.executeUpdate();
            return Optional.of(new ProductBatch(productBatchId, product, batchNo, eatByDate, discount, quantity));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteProductBatch(int productBatchId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("productBatch"));
            statement.setInt(1, productBatchId);

            statement.executeUpdate();
            return true;
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<ProductBatch> findProductBatchById(int productBatchId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productBatch_byId"));
            statement.setInt(1, productBatchId);

            ResultSet resultSet = statement.executeQuery();
            ProductBatch productBatch = null;
            if(resultSet.next()) {
                productBatch = new ProductBatchResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(productBatch);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<ProductBatch> findProductBatches() {
        List<ProductBatch> productBatches = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productBatch"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                productBatches.add(new ProductBatchResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            sqlException.getMessage();
        }
        return productBatches;
    }

    @Override
    public List<ProductBatch> findProductBatchesByProductId(int productId) {
        List<ProductBatch> productBatches = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productBatch_byProductId"));
            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                productBatches.add(new ProductBatchResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            sqlException.getMessage();
        }
        return productBatches;
    }

    @Override
    public List<ProductBatch> findProductBatchesWithDiscountAndProductId(int productId) {
        List<ProductBatch> productBatches = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productBatch_byProductIdWithDiscount"));
            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                productBatches.add(new ProductBatchResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            sqlException.getMessage();
        }
        return productBatches;
    }

    @Override
    public int countProductBatchAmount(int productBatchId) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("productBatch_quantityById"));
            statement.setInt(1, productBatchId);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                result = resultSet.getInt("RESULT");
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return result;
    }
}
