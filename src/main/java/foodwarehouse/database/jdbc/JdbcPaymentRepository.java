package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentRepository;
import foodwarehouse.core.data.paymentType.PaymentType;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPaymentRepository implements PaymentRepository {
    @Override
    public Optional<Payment> createPayment(PaymentType paymentType, float value) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Payment> updatePaymentValue(int paymentId, float value) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Payment> completePayment(int paymentId) throws SQLException {
        return Optional.empty();
    }

    @Override
    public boolean deletePayment(int paymentId) throws SQLException {
        return false;
    }

    @Override
    public Optional<Payment> findPaymentById(int paymentId) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Payment> findPayments() throws SQLException {
        return null;
    }
}
