package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FilteredSkillTest {

    // ===================== Constructor Tests =====================

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilteredSkill(null));
    }

    @Test
    public void constructor_emptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FilteredSkill(""));
    }

    @Test
    public void constructor_whitespaceOnly_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FilteredSkill("   "));
    }

    @Test
    public void constructor_validSkill_capitalizesFirstLetter() {
        FilteredSkill skill = new FilteredSkill("beginner");
        assertEquals("Beginner", skill.value);
    }

    @Test
    public void constructor_uppercaseInput_normalizesCorrectly() {
        FilteredSkill skill = new FilteredSkill("EXPERT");
        assertEquals("Expert", skill.value);
    }

    @Test
    public void constructor_mixedCaseInput_normalizesCorrectly() {
        FilteredSkill skill = new FilteredSkill("eXpErT");
        assertEquals("Expert", skill.value);
    }

    @Test
    public void constructor_inputWithLeadingTrailingWhitespace_trimsAndNormalizes() {
        FilteredSkill skill = new FilteredSkill("  beginner  ");
        assertEquals("Beginner", skill.value);
    }

    @Test
    public void constructor_singleCharacter_capitalizesCorrectly() {
        FilteredSkill skill = new FilteredSkill("a");
        assertEquals("A", skill.value);
    }

    @Test
    public void constructor_customSkillValue_storesCorrectly() {
        FilteredSkill skill = new FilteredSkill("advanced");
        assertEquals("Advanced", skill.value);
    }

    // ===================== capitalFirstLetter Tests =====================

    @Test
    public void capitalFirstLetter_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> FilteredSkill.capitalFirstLetter(null));
    }

    @Test
    public void capitalFirstLetter_lowercaseInput_capitalizesFirstLetter() {
        assertEquals("Beginner", FilteredSkill.capitalFirstLetter("beginner"));
    }

    @Test
    public void capitalFirstLetter_uppercaseInput_normalizesAndCapitalizes() {
        assertEquals("Expert", FilteredSkill.capitalFirstLetter("EXPERT"));
    }

    @Test
    public void capitalFirstLetter_mixedCaseInput_normalizesAndCapitalizes() {
        assertEquals("Intermediate", FilteredSkill.capitalFirstLetter("iNtErMeDiAtE"));
    }

    @Test
    public void capitalFirstLetter_inputWithWhitespace_trimsAndCapitalizes() {
        assertEquals("Beginner", FilteredSkill.capitalFirstLetter("  beginner  "));
    }

    @Test
    public void capitalFirstLetter_singleCharacter_capitalizesCorrectly() {
        assertEquals("A", FilteredSkill.capitalFirstLetter("a"));
    }

    // ===================== isValidFilteredSkill Tests =====================

    @Test
    public void isValidFilteredSkill_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> FilteredSkill.isValidFilteredSkill(null));
    }

    @Test
    public void isValidFilteredSkill_emptyString_returnsFalse() {
        assertFalse(FilteredSkill.isValidFilteredSkill(""));
    }

    @Test
    public void isValidFilteredSkill_whitespaceOnly_returnsFalse() {
        assertFalse(FilteredSkill.isValidFilteredSkill("   "));
    }

    @Test
    public void isValidFilteredSkill_validSkill_returnsTrue() {
        assertTrue(FilteredSkill.isValidFilteredSkill("beginner"));
    }

    @Test
    public void isValidFilteredSkill_validSkillWithWhitespace_returnsTrue() {
        assertTrue(FilteredSkill.isValidFilteredSkill("  beginner  "));
    }

    @Test
    public void isValidFilteredSkill_singleCharacter_returnsTrue() {
        assertTrue(FilteredSkill.isValidFilteredSkill("a"));
    }

    // ===================== toString Tests =====================

    @Test
    public void toString_returnsCapitalizedValue() {
        FilteredSkill skill = new FilteredSkill("beginner");
        assertEquals("Beginner", skill.toString());
    }

    @Test
    public void toString_mixedCaseInput_returnsNormalizedValue() {
        FilteredSkill skill = new FilteredSkill("eXpErT");
        assertEquals("Expert", skill.toString());
    }

    // ===================== equals Tests =====================

    @Test
    public void equals_sameObject_returnsTrue() {
        FilteredSkill skill = new FilteredSkill("beginner");
        assertTrue(skill.equals(skill));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        FilteredSkill skill1 = new FilteredSkill("beginner");
        FilteredSkill skill2 = new FilteredSkill("beginner");
        assertTrue(skill1.equals(skill2));
    }

    @Test
    public void equals_differentCase_returnsTrue() {
        FilteredSkill skill1 = new FilteredSkill("beginner");
        FilteredSkill skill2 = new FilteredSkill("BEGINNER");
        assertTrue(skill1.equals(skill2));
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        FilteredSkill skill1 = new FilteredSkill("beginner");
        FilteredSkill skill2 = new FilteredSkill("expert");
        assertFalse(skill1.equals(skill2));
    }

    @Test
    public void equals_null_returnsFalse() {
        FilteredSkill skill = new FilteredSkill("beginner");
        assertFalse(skill.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        FilteredSkill skill = new FilteredSkill("beginner");
        assertFalse(skill.equals("beginner"));
    }

    // ===================== hashCode Tests =====================

    @Test
    public void hashCode_sameValue_returnsSameHashCode() {
        FilteredSkill skill1 = new FilteredSkill("beginner");
        FilteredSkill skill2 = new FilteredSkill("beginner");
        assertEquals(skill1.hashCode(), skill2.hashCode());
    }

    @Test
    public void hashCode_differentValue_returnsDifferentHashCode() {
        FilteredSkill skill1 = new FilteredSkill("beginner");
        FilteredSkill skill2 = new FilteredSkill("expert");
        assertNotEquals(skill1.hashCode(), skill2.hashCode());
    }
}
