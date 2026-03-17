package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's skill level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSkill(String)}
 */
public class Skill {

    public static final String MESSAGE_CONSTRAINTS =
            "Skill level must be one of these values: beginner, intermediate, expert";

    public static final String SKILL_BEGINNER = "beginner";
    public static final String SKILL_INTERMEDIATE = "intermediate";
    public static final String SKILL_EXPERT = "expert";

    public final String value;

    /**
     * Constructs a {@code Skill}.
     *
     * @param skill A valid skill level.
     */
    public Skill(String skill) {
        requireNonNull(skill);
        String normalizedSkill = skill.trim().toLowerCase();
        checkArgument(isValidSkill(normalizedSkill), MESSAGE_CONSTRAINTS);
        value = capitalFirstLetter(normalizedSkill);
    }

    /**
     * Capitalizes the first letter of the given skill level string.
     *
     * <p>The input is trimmed of leading/trailing whitespace and converted to
     * lowercase before capitalization is applied.
     *
     * @param skillLevel the skill level string to capitalize; must not be null
     * @return the normalized string with its first letter capitalized
     * @throws NullPointerException if {@code skillLevel} is null
     */
    public static String capitalFirstLetter(String skillLevel) {
        requireNonNull(skillLevel);
        String normalizedSkill = skillLevel.trim().toLowerCase();
        return Character.toUpperCase(normalizedSkill.charAt(0)) + normalizedSkill.substring(1);
    }

    /**
     * Returns true if a given string is a valid skill level.
     */
    public static boolean isValidSkill(String test) {
        requireNonNull(test);
        String normalizedSkill = test.trim().toLowerCase();
        return normalizedSkill.equals(SKILL_BEGINNER)
                || normalizedSkill.equals(SKILL_INTERMEDIATE)
                || normalizedSkill.equals(SKILL_EXPERT);
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
        if (!(other instanceof Skill)) {
            return false;
        }

        Skill otherSkill = (Skill) other;
        return value.equals(otherSkill.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
