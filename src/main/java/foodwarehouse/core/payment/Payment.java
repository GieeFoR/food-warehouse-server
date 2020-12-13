package foodwarehouse.core.payment;

import foodwarehouse.core.paymentType.PaymentType;

public record Payment(
        int paymentId,
        PaymentType paymentType,
        float value,
        String state) {
}
