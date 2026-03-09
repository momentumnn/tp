package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.TrainingGoal;

public class TrainingGoalTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TrainingGoal(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTrainingGoal = "";
        assertThrows(IllegalArgumentException.class, () -> new TrainingGoal(invalidTrainingGoal));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> TrainingGoal.isValidTrainingGoal(null));
    }

}
