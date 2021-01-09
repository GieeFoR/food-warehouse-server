package foodwarehouse.core.data.paymentType;

import java.util.List;
import java.util.Optional;

public interface PaymentTypeRepository {

    Optional<PaymentType> createPaymentType(String type);

    Optional<PaymentType> updatePaymentType(int paymentTypeId, String type);

    boolean deletePaymentType(int paymentTypeId);

    Optional<PaymentType> findPaymentTypeById(int paymentTypeId);

    List<PaymentType> findPayments();
}
