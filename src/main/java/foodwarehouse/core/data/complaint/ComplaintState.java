package foodwarehouse.core.data.complaint;

import java.util.Optional;

public enum ComplaintState {
    REGISTERED("REGISTERED"),
    READ("READ"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    DECISION("DECISION"),
    CANCELED("CANCELED");

    private final String value;

    ComplaintState(String value) {
        this.value = value;
    }

    public static Optional<ComplaintState> from(String value) {
        return switch (value) {
            case "REGISTERED" -> Optional.of(ComplaintState.REGISTERED);
            case "READ" -> Optional.of(ComplaintState.READ);
            case "ACCEPTED" -> Optional.of(ComplaintState.ACCEPTED);
            case "REJECTED" -> Optional.of(ComplaintState.REJECTED);
            case "DECISION" -> Optional.of(ComplaintState.DECISION);
            case "CANCELED" -> Optional.of(ComplaintState.CANCELED);
            default -> Optional.empty();
        };
    }

    public String value() {
        return value;
    }
}
