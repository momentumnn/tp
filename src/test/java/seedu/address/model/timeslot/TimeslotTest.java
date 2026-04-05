package seedu.address.model.timeslot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimeslotTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Timeslot(null));
    }

    @Test
    public void constructor_invalidTimeslot_throwsIllegalArgumentException() {
        String invalidTimeslot = "Mon:0900-1010";
        assertThrows(IllegalArgumentException.class, () -> new Timeslot(invalidTimeslot));
    }

    @Test
    public void isValidTimeslot() {
        // null timeslot
        assertThrows(NullPointerException.class, () -> Timeslot.isValidTimeslot(null));

        // invalid timeslot
        assertFalse(Timeslot.isValidTimeslot("")); // empty string
        assertFalse(Timeslot.isValidTimeslot(" ")); // spaces only
        assertFalse(Timeslot.isValidTimeslot("mon: 1900-2000")); // space after colon
        assertFalse(Timeslot.isValidTimeslot("monday:1900-2000")); // days spelt in full
        assertFalse(Timeslot.isValidTimeslot("mon:2490-1680")); // invalid time
        assertFalse(Timeslot.isValidTimeslot("mon:1900-1500")); // start time later than end time
        assertFalse(Timeslot.isValidTimeslot("mon:1800-1800")); // same start and end time
        assertFalse(Timeslot.isValidTimeslot("mon:1800-2000")); // timings longer than an hour
        assertFalse(Timeslot.isValidTimeslot("mon:1800-1900,11")); // duplicate times
        assertFalse(Timeslot.isValidTimeslot("mon:-1")); // invalid slot
        assertFalse(Timeslot.isValidTimeslot("mon:13")); // invalid slot
        assertFalse(Timeslot.isValidTimeslot("mon:2,2;tue:4,4")); // duplicate slots
        assertFalse(Timeslot.isValidTimeslot("sun:")); // no slot chosen
        assertFalse(Timeslot.isValidTimeslot("sun:1;sun:2")); // duplicate days
        // overlapping times
        assertFalse(Timeslot.isValidTimeslot("mon:2100-2200,2030-2130"));
        assertFalse(Timeslot.isValidTimeslot("-")); // one character

        // valid timeslot
        assertTrue(Timeslot.isValidTimeslot("mon:1900-2000")); // day in lower case
        assertTrue(Timeslot.isValidTimeslot("MON:1900-2000")); // day in upper case
        assertTrue(Timeslot.isValidTimeslot("Mon:1900-2000")); // day in snake case
        assertTrue(Timeslot.isValidTimeslot("sun:1")); // single slot
        assertTrue(Timeslot.isValidTimeslot("sun:1,2,3,4")); // multiple slots
    }

    @Test
    public void equals() {
        Timeslot timeslot1 = new Timeslot("mon:0800-0900");
        Timeslot timeslot2 = new Timeslot("mon:2,4");

        // same values -> returns true
        assertTrue(timeslot1.equals(new Timeslot("mon:0800-0900")));
        assertTrue(timeslot2.equals(new Timeslot("mon:2,4")));

        // same object -> returns true
        assertTrue(timeslot1.equals(timeslot1));
        assertTrue(timeslot2.equals(timeslot2));

        // null -> returns false
        assertFalse(timeslot1.equals(null));
        assertFalse(timeslot2.equals(null));

        // different types -> returns false
        assertFalse(timeslot1.equals(5.0f));
        assertFalse(timeslot2.equals(5.0f));

        // different values -> returns false
        assertFalse(timeslot1.equals(new Timeslot("tue:0900-1000")));
        assertFalse(timeslot1.equals(new Timeslot("mon:2,4,6")));
    }
}
