package foodwarehouse.core.data.paymentType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaymentTypeRepository {

    Optional<PaymentType> createPaymentType(String type);

    Optional<PaymentType> updatePaymentType(int paymentId, String type);

    boolean deletePaymentType(int paymentId);

    Optional<PaymentType> findPaymentTypeById(int paymentId);

    List<PaymentType> findPayments();
}
