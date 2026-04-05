package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // EP: Null values
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // EP: Empty Strings
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only

        // EP: Contains invalid symbols
        assertFalse(Name.isValidName("^")); // non-alphabetic character
        assertFalse(Name.isValidName("peter*")); // contains symbol

        // EP: Contains numbers
        assertFalse(Name.isValidName("12345")); // numbers only (no longer valid)
        assertFalse(Name.isValidName("peter the 2nd")); // contains number

        // EP: Starts with valid symbols
        assertFalse(Name.isValidName("-John")); // starts with hyphen
        assertFalse(Name.isValidName("'John")); // starts with apostrophe
        assertFalse(Name.isValidName(" John")); // starts with space

        // EP: Max length
        assertFalse(Name.isValidName("A".repeat(101))); // 101 characters (too long)

        // EP: Valid length
        assertTrue(Name.isValidName("a".repeat(100))); // exactly 100 characters (boundary)
        assertTrue(Name.isValidName("a")); // exactly 1 character (boundary)

        // EP: Valid names
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray")); // long name

        // EP: Valid names with special characters
        assertTrue(Name.isValidName("Mary-Jane")); // hyphenated name
        assertTrue(Name.isValidName("O'Brien")); // apostrophe name
        assertTrue(Name.isValidName("José")); // accented characters
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
