package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.product.Product;
import foodwarehouse.core.data.product.ProductRepository;
import foodwarehouse.database.rowmappers.ProductResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.ProductTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
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

            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("product"), Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, maker.makerId());
            statement.setString(2, name);
            statement.setString(3, shortDesc);
            statement.setString(4, longDesc);
            statement.setString(5, category);
            statement.setString(6, needColdStorage?"Y" : "N");
            statement.setFloat(7, buyPrice);
            statement.setFloat(8, sellPrice);
            statement.setBlob(9, blob);

            statement.executeUpdate();
            int productId = statement.getGeneratedKeys().getInt(1);
            return Optional.of(new Product(productId, maker, name, shortDesc, longDesc, category, needColdStorage, buyPrice, sellPrice, image));
        }
        catch (SQLException | FileNotFoundException sqlException) {
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

            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("product"));
            statement.setString(1, name);
            statement.setString(2, shortDesc);
            statement.setString(3, longDesc);
            statement.setString(4, category);
            statement.setString(5, needColdStorage?"Y" : "N");
            statement.setFloat(6, buyPrice);
            statement.setFloat(7, sellPrice);
            statement.setBlob(8, blob);
            statement.setInt(9, productId);


            statement.executeUpdate();
            return Optional.of(new Product(productId, maker, name, shortDesc, longDesc, category, needColdStorage, buyPrice, sellPrice, image));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("product"));
            statement.setInt(1, productId);

            statement.executeUpdate();
            return true;
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Product> findProductById(int productId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("product_byId"));
            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();
            Product product = null;
            if(resultSet.next()) {
                product = new ProductResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(product);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findProducts() {
        List<Product> products = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("product"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                products.add(new ProductResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> findAvailableProducts() {
        List<Product> products = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("product_available"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                products.add(new ProductResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return products;
    }

    @Override
    public int countAmountOfProducts(int productId) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("product_quantityById"));
            statement.setInt(1, productId);

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

    @Override
    public List<Product> findRunningOutProducts() {
        List<Product> products = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("product_runningOut"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                products.add(new ProductResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> topTenProducts() {
        List<Product> products = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("product_topTen"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                products.add(new ProductResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return products;
    }
}
