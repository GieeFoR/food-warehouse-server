package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.product.ProductRepository;
import foodwarehouse.database.rowmappers.ProductResultSetMapper;
import foodwarehouse.database.tables.ProductTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final Connection connection;

    @Autowired
    JdbcProductRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<Product> createProduct(
            Maker maker,
            String name,
            String shortDesc,
            String longDesc,
            String category,
            boolean needColdStorage,
            float buyPrice,
            float sellPrice,
            String image) {

        try {
            Blob blob = connection.createBlob();
            blob.setBytes(1, image.getBytes());

            CallableStatement callableStatement = connection.prepareCall(ProductTable.Procedures.INSERT);
            callableStatement.setInt(1, maker.makerId());
            callableStatement.setString(2, name);
            callableStatement.setString(3, shortDesc);
            callableStatement.setString(4, longDesc);
            callableStatement.setString(5, category);
            callableStatement.setBoolean(6, needColdStorage);
            callableStatement.setFloat(7, buyPrice);
            callableStatement.setFloat(8, sellPrice);
            callableStatement.setBlob(9, blob);

            callableStatement.executeQuery();
            int productId = callableStatement.getInt(10);
            return Optional.of(new Product(productId, maker, name, shortDesc, longDesc, category, needColdStorage, buyPrice, sellPrice, image));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> updateProduct(
            int productId,
            Maker maker,
            String name,
            String shortDesc,
            String longDesc,
            String category,
            boolean needColdStorage,
            float buyPrice,
            float sellPrice,
            String image) {

        try {
            Blob blob = connection.createBlob();
            blob.setBytes(1, image.getBytes());

            CallableStatement callableStatement = connection.prepareCall(ProductTable.Procedures.UPDATE);
            callableStatement.setInt(1, productId);
            callableStatement.setString(2, name);
            callableStatement.setString(3, shortDesc);
            callableStatement.setString(4, longDesc);
            callableStatement.setString(5, category);
            callableStatement.setBoolean(6, needColdStorage);
            callableStatement.setFloat(7, buyPrice);
            callableStatement.setFloat(8, sellPrice);
            callableStatement.setBlob(9, blob);

            callableStatement.executeQuery();
            return Optional.of(new Product(productId, maker, name, shortDesc, longDesc, category, needColdStorage, buyPrice, sellPrice, image));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductTable.Procedures.DELETE);
            callableStatement.setInt(1, productId);

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<Product> findProductById(int productId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, productId);

            ResultSet resultSet = callableStatement.executeQuery();
            Product product = null;
            if(resultSet.next()) {
                product = new ProductResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(product);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findProducts() {
        List<Product> products = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                products.add(new ProductResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return products;
    }

    @Override
    public List<Product> findAvailableProducts() {
        List<Product> products = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductTable.Procedures.READ_AVAILABLE);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                products.add(new ProductResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return products;
    }

    @Override
    public int countAmountOfProducts(int productId) {
        int result = 0;
        try {
            CallableStatement callableStatement = connection.prepareCall(ProductTable.Procedures.GET_PRODUCT_QUANTITY_WITH_REGULAR_PRICE_AND_PRODUCT_ID);
            callableStatement.setInt(1, productId);

            ResultSet resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
                result = resultSet.getInt("RESULT");
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return result;
    }
}
