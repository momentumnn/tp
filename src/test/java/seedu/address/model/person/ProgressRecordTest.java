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

        // invalid progress record
        assertFalse(ProgressRecord.isValidProgress("")); // empty string
        assertFalse(ProgressRecord.isValidProgress(" ")); // spaces only
        assertFalse(ProgressRecord.isValidProgress("101%")); // more than 100%
        assertFalse(ProgressRecord.isValidProgress("-1%")); //less than 0%
        assertFalse(ProgressRecord.isValidProgress("10/0"));

        // valid progress record
        assertTrue(ProgressRecord.isValidProgress("100%"));
        assertTrue(ProgressRecord.isValidProgress("0%")); // one character
        assertTrue(ProgressRecord.isValidProgress("50%"));
    }

    @Test
    public void parseToPercentage() {
        //divide by zero
        assertThrows(NumberFormatException.class, () -> ProgressRecord.parseToPercentage("1/0"));

        assertTrue(ProgressRecord.parseToPercentage("50%") == 50);
        assertTrue(ProgressRecord.parseToPercentage("100%") == 100);
        assertTrue(ProgressRecord.parseToPercentage("0%") == 0);

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

    @Test
    public void compareTo() {
        ProgressRecord pr0 = new ProgressRecord("0%");
        ProgressRecord pr50 = new ProgressRecord("50%");
        ProgressRecord pr100 = new ProgressRecord("100%");

        assertTrue(pr0.compareTo(pr50) < 0);
        assertTrue(pr100.compareTo(pr0) > 0);
        assertFalse(pr0.compareTo(pr50) == 0);
        assertFalse(pr100.compareTo(pr0) < 0);
        assertTrue(pr0.compareTo(pr0) == 0);

    }
}
