package foodwarehouse.core.data.payment;

import foodwarehouse.core.data.paymentType.PaymentType;

public record Payment(
        int paymentId,
        PaymentType paymentType,
        float value,
        PaymentState state) {
}
