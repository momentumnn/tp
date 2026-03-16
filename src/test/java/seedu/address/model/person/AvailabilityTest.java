package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AvailabilityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Availability(null));
    }

    @Test
    public void constructor_invalidAvailability_throwsIllegalArgumentException() {
        String invalidAvailability = "Sun: 0900-1000; Mon:0900-1000";
        assertThrows(IllegalArgumentException.class, () -> new Availability(invalidAvailability));
    }

    @Test
    public void isValidAvailability() {
        // null availability
        assertThrows(NullPointerException.class, () -> Availability.isValidAvailability(null));

        // invalid availability
        assertFalse(Availability.isValidAvailability("")); // empty string
        assertFalse(Availability.isValidAvailability(" ")); // spaces only
        assertFalse(Availability.isValidAvailability("mon:1900-2000; sat:2000-2100")); // space in between days
        assertFalse(Availability.isValidAvailability("mon: 1900-2000;sat: 2000-2100")); // space in each day
        assertFalse(Availability.isValidAvailability("monday:1900-2000;saturday:2000-2100")); // days spelt in full
        assertFalse(Availability.isValidAvailability("mon:2490-1680;sat:-900+1230")); // invalid time
        assertFalse(Availability.isValidAvailability("mon:2100-1500")); // start time later than end time
        assertFalse(Availability.isValidAvailability("mon:2100-2100")); // same start and end time
        assertFalse(Availability.isValidAvailability("mon:2100-2200;mon:2100-2200")); // duplicate days
        assertFalse(Availability.isValidAvailability("mon:2100-2200,tue:2100-2200")); // wrong delimitter between days
        // overlapping times
        assertFalse(Availability.isValidAvailability("mon:2100-2200,2030-2130;tue:2100-2200"));
        assertFalse(Availability.isValidAvailability("-")); // one character

        // valid availability
        assertTrue(Availability.isValidAvailability("mon:1900-2000;sat:2000-2100")); // days in lower case
        assertTrue(Availability.isValidAvailability("MON:1900-2000;SAT:2000-2100")); // days in upper case
        assertTrue(Availability.isValidAvailability("Mon:1900-2000;Sat:2000-2100")); // days in snake case
        // multiple time for each day
        assertTrue(Availability.isValidAvailability("Mon:1900-2000,2000-2100;Sat:2000-2100,0800-1000"));
        assertTrue(Availability.isValidAvailability("mon:1900-2000;tue:0800-1000;wed:1600-1700;"
                + "thu:0900-1000;fri:1700-1730;sat:2000-2100;sun:2000-2100")); // long availability
    }

    @Test
    public void equals() {
        Availability availability = new Availability("mon:0800-0900");

        // same values -> returns true
        assertTrue(availability.equals(new Availability("mon:0800-0900")));

        // same object -> returns true
        assertTrue(availability.equals(availability));

        // null -> returns false
        assertFalse(availability.equals(null));

        // different types -> returns false
        assertFalse(availability.equals(5.0f));

        // different values -> returns false
        assertFalse(availability.equals(new Availability("tue:0900-1000")));
    }
}
