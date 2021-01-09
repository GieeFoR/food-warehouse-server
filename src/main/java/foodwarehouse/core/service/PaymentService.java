package foodwarehouse.core.service;

import foodwarehouse.core.data.payment.Payment;
import foodwarehouse.core.data.payment.PaymentRepository;
import foodwarehouse.core.data.payment.PaymentState;
import foodwarehouse.core.data.paymentType.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Optional<Payment> createPayment(PaymentType paymentType, float value) {
        return paymentRepository.createPayment(paymentType, value);
    }

    public Optional<Payment> updatePaymentValue(int paymentId, float value) {
        return paymentRepository.updatePaymentValue(paymentId, value);
    }

    public Optional<Payment> updatePaymentState(int paymentId, PaymentState state) {
        return paymentRepository.updatePaymentState(paymentId, state);
    }

    public boolean deletePayment(int paymentId) {
        return paymentRepository.deletePayment(paymentId);
    }

    public Optional<Payment> findPaymentById(int paymentId) {
        return paymentRepository.findPaymentById(paymentId);
    }

    public List<Payment> findPayments() {
        return paymentRepository.findPayments();
    }

    public List<Payment> findCustomerPayments(String username) {
        return paymentRepository.findCustomerPayments(username);
    }
}
