package foodwarehouse.core.data.order;

import foodwarehouse.core.data.complaint.ComplaintState;

import java.util.Optional;

public enum OrderState {
    REGISTERED("REGISTERED"),
    CANCELED("CANCELED"),
    COMPLETING("COMPLETING"),
    READY_TO_DELIVER("READY TO DELIVER"),
    OUT_FOR_DELIVERY("OUT FOR DELIVERY"),
    DELIVERED("DELIVERED"),
    RETURNED("RETURNED");

    private final String value;

    OrderState(String value) {
        this.value = value;
    }

    public static Optional<OrderState> from(String value) {
        return switch (value) {
            case "REGISTERED" -> Optional.of(OrderState.REGISTERED);
            case "CANCELED" -> Optional.of(OrderState.CANCELED);
            case "COMPLETING" -> Optional.of(OrderState.COMPLETING);
            case "READY TO DELIVER" -> Optional.of(OrderState.READY_TO_DELIVER);
            case "OUT FOR DELIVERY" -> Optional.of(OrderState.OUT_FOR_DELIVERY);
            case "DELIVERED" -> Optional.of(OrderState.DELIVERED);
            case "RETURNED" -> Optional.of(OrderState.RETURNED);
            default -> Optional.empty();
        };
    }

    public String value() {
        return value;
    }
}
