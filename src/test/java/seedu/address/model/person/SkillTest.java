package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SkillTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Skill(null));
    }

    @Test
    public void constructor_invalidSkill_throwsIllegalArgumentException() {
        String invalidSkill = "";
        assertThrows(IllegalArgumentException.class, () -> new Skill(invalidSkill));
    }

    @Test
    public void capitalFirstLetter() {
        assertThrows(NullPointerException.class, () -> Skill.capitalFirstLetter(null));

        // equals test
        assertEquals("Beginner", Skill.capitalFirstLetter("beginner"));
        assertEquals("Intermediate", Skill.capitalFirstLetter("intermediate"));
        assertEquals("Expert", Skill.capitalFirstLetter("expert"));
        assertEquals("Beginner", Skill.capitalFirstLetter("beginNer"));
        assertEquals("Intermediate", Skill.capitalFirstLetter("iNterMediate"));
        assertEquals("Expert", Skill.capitalFirstLetter("eXpErt"));
        assertEquals("123expert", Skill.capitalFirstLetter("123expert"));

        // not equals test
        assertNotEquals("beginner", Skill.capitalFirstLetter("beginner"));
        assertNotEquals("beginner", Skill.capitalFirstLetter("Beginner"));
        assertNotEquals("eXpert", Skill.capitalFirstLetter("eXpert"));
    }

    @Test
    public void isValidSkill() {
        // null skill
        assertThrows(NullPointerException.class, () -> Skill.isValidSkill(null));

        // invalid skill levels
        assertFalse(Skill.isValidSkill("")); // empty string
        assertFalse(Skill.isValidSkill(" ")); // spaces only
        assertFalse(Skill.isValidSkill("master")); // unsupported skill level
        assertFalse(Skill.isValidSkill("novice"));
        assertFalse(Skill.isValidSkill("pro"));

        // valid skill levels
        assertTrue(Skill.isValidSkill("beginNer")); // unsupported skill level
        assertTrue(Skill.isValidSkill("inteRmediate"));
        assertTrue(Skill.isValidSkill("eXpert"));
    }

    @Test
    public void equals() {
        Skill skill = new Skill("beginner");

        // same values -> returns true
        assertTrue(skill.equals(new Skill("Beginner")));

        // same object -> returns true
        assertTrue(skill.equals(skill));

        // null -> returns false
        assertFalse(skill.equals(null));

        // different types -> returns false
        assertFalse(skill.equals(5.0f));

        // different values -> returns false
        assertFalse(skill.equals(new Skill("exPert")));
    }
}
