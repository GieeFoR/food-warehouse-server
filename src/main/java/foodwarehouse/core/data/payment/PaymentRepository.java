package foodwarehouse.core.data.payment;

import foodwarehouse.core.data.paymentType.PaymentType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    Optional<Payment> createPayment(PaymentType paymentType, float value);

    Optional<Payment> updatePaymentValue(int paymentId, float value);

    Optional<Payment> completePayment(int paymentId);

    boolean deletePayment(int paymentId);

    Optional<Payment> findPaymentById(int paymentId);

    List<Payment> findPayments();
}
