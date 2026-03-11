package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class InjuryStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Injury status should not be blank.";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public static final String DEFAULT_INJURY_STATUS = "Unknown";

    public final String value;

    public InjuryStatus(String injuryStatus) {
        requireNonNull(injuryStatus);
        checkArgument(isValidInjuryStatus(injuryStatus), MESSAGE_CONSTRAINTS);
        value = injuryStatus;
    }

    public static boolean isValidInjuryStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof InjuryStatus
                && value.equals(((InjuryStatus) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}