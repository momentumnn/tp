package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a filter criterion based on a Person's skill level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFilteredSkill(String)}
 *
 * <p>Unlike {@code Skill}, this class accepts any non-blank string as a valid skill level,
 * allowing flexible filtering without being restricted to predefined skill levels.
 */
public class FilteredSkill {

    public static final String MESSAGE_CONSTRAINTS =
            "Skill level must not be empty";

    public final String value;

    /**
     * Constructs a {@code FilteredSkill} with the given skill level string.
     *
     * <p>The input is trimmed and normalized to lowercase before having its
     * first letter capitalized.
     *
     * @param skill a non-blank skill level string; must not be null
     * @throws NullPointerException if {@code skill} is null
     * @throws IllegalArgumentException if {@code skill} is blank
     */
    public FilteredSkill(String skill) {
        requireNonNull(skill);

        String normalizedSkill = skill.trim().toLowerCase();
        checkArgument(isValidFilteredSkill(normalizedSkill), MESSAGE_CONSTRAINTS);
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
     * Returns true if the given string is a valid (non-blank) skill level.
     *
     * @param test the string to validate; must not be null
     * @return true if {@code test} is not blank after trimming, false otherwise
     * @throws NullPointerException if {@code test} is null
     */
    public static boolean isValidFilteredSkill(String test) {
        requireNonNull(test);
        String normalizedSkill = test.trim().toLowerCase();
        return !normalizedSkill.isBlank();
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
        if (!(other instanceof FilteredSkill)) {
            return false;
        }

        FilteredSkill otherSkill = (FilteredSkill) other;
        return value.equalsIgnoreCase(otherSkill.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
