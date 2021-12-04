package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.core.data.paymentType.PaymentTypeRepository;
import foodwarehouse.database.rowmappers.PaymentTypeResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.PaymentTypeTable;
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
public class JdbcPaymentTypeRepository implements PaymentTypeRepository {

//    private final Connection connection;
//
//    @Autowired
//    JdbcPaymentTypeRepository(DataSource dataSource) {
//        try {
//            this.connection = dataSource.getConnection();
//        }
//        catch(SQLException sqlException) {
//            throw new RestException("Cannot connect to database!");
//        }
//    }

    @Override
    public Optional<PaymentType> createPaymentType(String type) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("paymentType"), Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, type);

                statement.executeUpdate();
                int paymentTypeId = statement.getGeneratedKeys().getInt(1);
                statement.close();

                return Optional.of(new PaymentType(paymentTypeId, type));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<PaymentType> updatePaymentType(int paymentTypeId, String type) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("paymentType"));
                statement.setString(1, type);
                statement.setInt(2, paymentTypeId);

                statement.executeUpdate();
                statement.close();

                return Optional.of(new PaymentType(paymentTypeId, type));
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deletePaymentType(int paymentTypeId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("paymentType"));
                statement.setInt(1, paymentTypeId);

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
    public Optional<PaymentType> findPaymentTypeById(int paymentTypeId) {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("paymentType_byId"));
                statement.setInt(1, paymentTypeId);

                ResultSet resultSet = statement.executeQuery();
                PaymentType paymentType = null;
                if(resultSet.next()) {
                    paymentType = new PaymentTypeResultSetMapper().resultSetMap(resultSet, "");
                }
                statement.close();
                statement.close();
                return Optional.ofNullable(paymentType);
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<PaymentType> findPayments() {
        List<PaymentType> paymentTypes = new LinkedList<>();
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("paymentType"));

                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    paymentTypes.add(new PaymentTypeResultSetMapper().resultSetMap(resultSet, ""));
                }
                statement.close();
            }
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            paymentTypes = null;
        }
        return paymentTypes;
    }
}
