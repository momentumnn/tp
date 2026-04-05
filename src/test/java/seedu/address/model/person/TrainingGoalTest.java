package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TrainingGoalTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TrainingGoal(null));
    }

    @Test
    public void constructor_invalidTrainingGoal_throwsIllegalArgumentException() {
        // EP: empty Strings
        assertThrows(IllegalArgumentException.class, () -> new TrainingGoal(""));
        assertThrows(IllegalArgumentException.class, () -> new TrainingGoal("   "));

        // EP: flag-like patterns
        assertThrows(IllegalArgumentException.class, () -> new TrainingGoal("x/something"));
        assertThrows(IllegalArgumentException.class, () -> new TrainingGoal("ab/something"));
        assertThrows(IllegalArgumentException.class, () -> new TrainingGoal("run 50km x/weekly"));
    }

    @Test
    public void isValidTrainingGoal() {
        // EP: null values
        assertThrows(NullPointerException.class, () -> TrainingGoal.isValidTrainingGoal(null));

        // EP: empty Strings
        assertFalse(TrainingGoal.isValidTrainingGoal("")); // empty string
        assertFalse(TrainingGoal.isValidTrainingGoal(" ")); // spaces only

        // EP: flag-like patterns
        assertFalse(TrainingGoal.isValidTrainingGoal("x/something")); // single letter flag-like pattern
        assertFalse(TrainingGoal.isValidTrainingGoal("ab/something")); // two letter flag-like pattern
        assertFalse(TrainingGoal.isValidTrainingGoal("run 50km x/weekly")); // flag-like pattern in middle

        // BVA: max length
        assertTrue(TrainingGoal.isValidTrainingGoal("a".repeat(200))); // exactly 200 characters (max boundary)
        assertFalse(TrainingGoal.isValidTrainingGoal("a".repeat(201))); // 201 characters (too long)

        // EP: valid training goals
        assertTrue(TrainingGoal.isValidTrainingGoal("a")); // single character
        assertTrue(TrainingGoal.isValidTrainingGoal("run 50km")); // typical goal
        assertTrue(TrainingGoal.isValidTrainingGoal("lift 100kg (bench press)")); // with symbols
        assertTrue(TrainingGoal.isValidTrainingGoal("run 5k/day")); // number + letters before slash allowed
        assertTrue(TrainingGoal.isValidTrainingGoal("run 50/100km")); // numbers before slash allowed
    }

    @Test
    public void equals() {
        TrainingGoal goal = new TrainingGoal("run 50km");

        // same values -> returns true
        assertTrue(goal.equals(new TrainingGoal("run 50km")));

        // same object -> returns true
        assertTrue(goal.equals(goal));

        // null -> returns false
        assertFalse(goal.equals(null));

        // different types -> returns false
        assertFalse(goal.equals(5.0f));

        // different values -> returns false
        assertFalse(goal.equals(new TrainingGoal("lift 100kg")));
    }

    @Test
    public void hashCode_sameValue_returnsSameHashCode() {
        assertEquals(new TrainingGoal("run 50km").hashCode(), new TrainingGoal("run 50km").hashCode());
    }

    @Test
    public void hashCode_differentValue_returnsDifferentHashCode() {
        assertNotEquals(new TrainingGoal("run 50km").hashCode(), new TrainingGoal("lift 100kg").hashCode());
    }

    @Test
    public void toString_returnsValue() {
        assertEquals("run 50km", new TrainingGoal("run 50km").toString());
    }
}
