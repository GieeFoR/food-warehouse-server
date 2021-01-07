package foodwarehouse.core.data.payment;

import foodwarehouse.core.data.paymentType.PaymentType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    Optional<Payment> createPayment(PaymentType paymentType, float Value) throws SQLException;

    Optional<Payment> updatePaymentValue(int paymentId, float value) throws SQLException;

    Optional<Payment> completePayment(int paymentId) throws SQLException;

    boolean deletePayment(int paymentId) throws SQLException;

    Optional<Payment> findPaymentById(int paymentId) throws SQLException;

    List<Payment> findPayments() throws SQLException;
}
