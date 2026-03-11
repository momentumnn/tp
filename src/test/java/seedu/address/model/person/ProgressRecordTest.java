package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProgressRecordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProgressRecord(null));
    }

    @Test
    public void constructor_invalidProgressRecord_throwsIllegalArgumentException() {
        String invalidProgressRecord = "";
        assertThrows(IllegalArgumentException.class, () -> new ProgressRecord(invalidProgressRecord));
    }

    @Test
    public void isValidProgressRecord() {
        // null ProgressRecord
        assertThrows(NullPointerException.class, () -> ProgressRecord.isValidProgress(null));

        // invalid addresses
        assertFalse(ProgressRecord.isValidProgress("")); // empty string
        assertFalse(ProgressRecord.isValidProgress(" ")); // spaces only

        // valid addresses
        assertTrue(ProgressRecord.isValidProgress("100%"));
        assertTrue(ProgressRecord.isValidProgress("0%")); // one character
        assertTrue(ProgressRecord.isValidProgress("50%"));
    }

    @Test
    public void equals() {
        ProgressRecord pr = new ProgressRecord("0%");

        // same values -> returns true
        assertTrue(pr.equals(new ProgressRecord("0%")));

        // same object -> returns true
        assertTrue(pr.equals(pr));

        // null -> returns false
        assertFalse(pr.equals(null));

        // different types -> returns false
        assertFalse(pr.equals(5.0f));

        // different values -> returns false
        assertFalse(pr.equals(new ProgressRecord("100%")));
    }
}
