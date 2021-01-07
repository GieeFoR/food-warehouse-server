package foodwarehouse.core.service;

import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.core.data.paymentType.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;

    @Autowired
    public PaymentTypeService(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    public Optional<PaymentType> createPaymentType(String type) throws SQLException {
        return paymentTypeRepository.createPaymentType(type);
    }

    public Optional<PaymentType> updatePaymentType(int paymentId, String type) throws SQLException {
        return paymentTypeRepository.updatePaymentType(paymentId, type);
    }

    public boolean deletePaymentType(int paymentId) throws SQLException {
        return paymentTypeRepository.deletePaymentType(paymentId);
    }

    public Optional<PaymentType> findPaymentTypeById(int paymentId) throws SQLException {
        return paymentTypeRepository.findPaymentTypeById(paymentId);
    }

    public List<PaymentType> findPayments() throws SQLException {
        return paymentTypeRepository.findPayments();
    }
}
