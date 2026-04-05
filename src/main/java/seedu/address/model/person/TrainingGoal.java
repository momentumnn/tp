package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's training goal in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTrainingGoal(String)}
 */
public class TrainingGoal {

    public static final String MESSAGE_CONSTRAINTS =
            "Training Goal should not be blank, should not contain flag-like patterns (e.g. 'x/', 'ab/'), "
                    + "and should be at most 200 characters long.";

    public static final String VALIDATION_REGEX = "(?!.*\\b[a-zA-Z]{1,2}/)[^\\s].{0,199}";

    public final String value;

    /**
     * Constructs an {@code training goal}.
     *
     * @param trainingGoal A valid trainingGoal.
     */
    public TrainingGoal(String trainingGoal) {
        requireNonNull(trainingGoal);
        checkArgument(isValidTrainingGoal(trainingGoal), MESSAGE_CONSTRAINTS);
        value = trainingGoal;
    }

    /**
     * Returns true if a given string is a valid training goal.
     */
    public static boolean isValidTrainingGoal(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TrainingGoal)) {
            return false;
        }

        TrainingGoal otherTrainingGoal = (TrainingGoal) other;
        return value.equals(otherTrainingGoal.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
