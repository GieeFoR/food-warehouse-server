package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentRepository;
import foodwarehouse.core.data.payment.PaymentState;
import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.database.rowmappers.PaymentResultSetMapper;
import foodwarehouse.database.statements.ReadStatement;
import foodwarehouse.database.tables.PaymentTable;
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
public class JdbcPaymentRepository implements PaymentRepository {

    private final Connection connection;

    @Autowired
    JdbcPaymentRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<Payment> createPayment(PaymentType paymentType, float value) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readInsert("payment"), Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, paymentType.paymentTypeId());
            statement.setFloat(2, value);

            statement.executeUpdate();
            int paymentId = statement.getGeneratedKeys().getInt(1);
            return Optional.of(new Payment(paymentId, paymentType, value, PaymentState.IN_PROGRESS));
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Payment> updatePaymentValue(int paymentId, float value) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("payment_value"));
            statement.setInt(1, paymentId);
            statement.setInt(2, paymentId);
            statement.setFloat(3, value);
            statement.setInt(4, paymentId);
            statement.setInt(5, paymentId);

            ResultSet resultSet = statement.executeQuery();
            Payment payment = null;
            if (resultSet.next()) {
                payment = new PaymentResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(payment);
        } catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Payment> updatePaymentState(int paymentId, PaymentState state) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readUpdate("payment_state"));
            statement.setInt(1, paymentId);
            statement.setInt(2, paymentId);
            statement.setObject(3, state.value());
            statement.setInt(4, paymentId);
            statement.setInt(5, paymentId);

            ResultSet resultSet = statement.executeQuery();
            Payment payment = null;
            if(resultSet.next()) {
                payment = new PaymentResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(payment);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deletePayment(int paymentId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readDelete("payment"));
            statement.setInt(1, paymentId);

            statement.executeUpdate();
            return true;
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<Payment> findPaymentById(int paymentId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("payment_byId"));
            statement.setInt(1, paymentId);

            ResultSet resultSet = statement.executeQuery();
            Payment payment = null;
            if(resultSet.next()) {
                payment = new PaymentResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(payment);
        }
        catch (SQLException | FileNotFoundException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Payment> findPayments() {
        List<Payment> payments = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("payment"));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                payments.add(new PaymentResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return payments;
    }

    @Override
    public List<Payment> findCustomerPayments(String username) {
        List<Payment> payments = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(ReadStatement.readSelect("payment_byUsername"));
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                payments.add(new PaymentResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException | FileNotFoundException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return payments;
    }
}
