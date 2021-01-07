package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.core.data.paymentType.PaymentTypeRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPaymentTypeRepository implements PaymentTypeRepository {
    @Override
    public Optional<PaymentType> createPaymentType(String type) {
        return Optional.empty();
    }

    @Override
    public Optional<PaymentType> updatePaymentType(int paymentId, String type) {
        return Optional.empty();
    }

    @Override
    public boolean deletePaymentType(int paymentId) {
        return false;
    }

    @Override
    public Optional<PaymentType> findPaymentTypeById(int paymentId) {
        return Optional.empty();
    }

    @Override
    public List<PaymentType> findPayments() {
        return null;
    }
}
