package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentRepository;
import foodwarehouse.core.data.payment.PaymentState;
import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.database.rowmappers.PaymentResultSetMapper;
import foodwarehouse.database.tables.PaymentTable;
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
            CallableStatement callableStatement = connection.prepareCall(PaymentTable.Procedures.INSERT);
            callableStatement.setInt(1, paymentType.paymentTypeId());
            callableStatement.setFloat(2, value);

            callableStatement.executeQuery();
            int paymentId = callableStatement.getInt(3);
            return Optional.of(new Payment(paymentId, paymentType, value, PaymentState.IN_PROGRESS));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Payment> updatePaymentValue(int paymentId, float value) {
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTable.Procedures.UPDATE_VALUE);
            callableStatement.setInt(1, paymentId);
            callableStatement.setFloat(2, value);

            ResultSet resultSet = callableStatement.executeQuery();
            Payment payment = null;
            if(resultSet.next()) {
                payment = new PaymentResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(payment);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Payment> updatePaymentState(int paymentId, PaymentState state) {
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTable.Procedures.COMPLETE);
            callableStatement.setInt(1, paymentId);
            callableStatement.setString(2, state.value());

            ResultSet resultSet = callableStatement.executeQuery();
            Payment payment = null;
            if(resultSet.next()) {
                payment = new PaymentResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(payment);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deletePayment(int paymentId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTable.Procedures.DELETE);
            callableStatement.setInt(1, paymentId);

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<Payment> findPaymentById(int paymentId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, paymentId);

            ResultSet resultSet = callableStatement.executeQuery();
            Payment payment = null;
            if(resultSet.next()) {
                payment = new PaymentResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(payment);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Payment> findPayments() {
        List<Payment> payments = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                payments.add(new PaymentResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return payments;
    }
}
