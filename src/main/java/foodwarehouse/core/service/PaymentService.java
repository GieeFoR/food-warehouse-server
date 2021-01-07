package foodwarehouse.core.service;

import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentRepository;
import foodwarehouse.core.data.paymentType.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Optional<Payment> createPayment(PaymentType paymentType, float value) throws SQLException {
        return paymentRepository.createPayment(paymentType, value);
    }

    public Optional<Payment> updatePaymentValue(int paymentId, float value) throws SQLException {
        return paymentRepository.updatePaymentValue(paymentId, value);
    }

    public Optional<Payment> completePayment(int paymentId) throws SQLException {
        return paymentRepository.completePayment(paymentId);
    }

    public boolean deletePayment(int paymentId) throws SQLException {
        return paymentRepository.deletePayment(paymentId);
    }

    public Optional<Payment> findPaymentById(int paymentId) throws SQLException {
        return paymentRepository.findPaymentById(paymentId);
    }

    public List<Payment> findPayments() throws SQLException {
        return paymentRepository.findPayments();
    }
}