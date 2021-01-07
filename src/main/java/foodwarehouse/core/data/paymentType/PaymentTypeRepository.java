package foodwarehouse.core.data.paymentType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaymentTypeRepository {

    Optional<PaymentType> createPaymentType(String type) throws SQLException;

    Optional<PaymentType> updatePaymentType(int paymentId, String type) throws SQLException;

    boolean deletePaymentType(int paymentId) throws SQLException;

    Optional<PaymentType> findPaymentTypeById(int paymentId) throws SQLException;

    List<PaymentType> findPayments() throws SQLException;
}
