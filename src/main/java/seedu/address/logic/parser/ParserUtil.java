package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Availability;
import seedu.address.model.person.Email;
import seedu.address.model.person.FilteredSkill;
import seedu.address.model.person.InjuryStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProgressRecord;
import seedu.address.model.person.Skill;
import seedu.address.model.person.TrainingGoal;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String availability} into an {@code Availability}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code availability} is invalid.
     */
    public static Availability parseAvailability(String availability) throws ParseException {
        requireNonNull(availability);
        String trimmedAvailability = availability.trim();
        if (!Availability.isValidAvailability(trimmedAvailability)) {
            throw new ParseException(Availability.MESSAGE_CONSTRAINTS);
        }
        return new Availability(trimmedAvailability);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String injuryStatus} into an {@code InjuryStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code injuryStatus} is invalid.
     */
    public static InjuryStatus parseInjuryStatus(String injuryStatus) throws ParseException {
        requireNonNull(injuryStatus);
        String trimmedInjuryStatus = injuryStatus.trim();
        if (!InjuryStatus.isValidInjuryStatus(trimmedInjuryStatus)) {
            throw new ParseException(InjuryStatus.MESSAGE_CONSTRAINTS);
        }
        return new InjuryStatus(trimmedInjuryStatus);
    }

    /**
     * Parses a {@code String trainingGoal} into an {@code TrainingGoal}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code trainingGoal} is invalid.
     */
    public static TrainingGoal parseTrainingGoal(String trainingGoal) throws ParseException {
        requireNonNull(trainingGoal);
        String trimmedTrainingGoal = trainingGoal.trim();
        if (!TrainingGoal.isValidTrainingGoal(trimmedTrainingGoal)) {
            throw new ParseException(TrainingGoal.MESSAGE_CONSTRAINTS);
        }
        return new TrainingGoal(trimmedTrainingGoal);
    }

    /**
     * Parses an {@code Optional<String> skill} into a {@code Skill}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @return parsed {@code Skill}, or novice if the
     *         skill value is not present.
     * @throws ParseException if the given {@code skill} is invalid.
     */
    public static Skill parseSkill(Optional<String> skill) throws ParseException {
        requireNonNull(skill);
        if (skill.isEmpty()) {
            return new Skill(Skill.SKILL_BEGINNER);
        }

        String trimmedSkill = skill.get().trim();
        if (!Skill.isValidSkill(trimmedSkill)) {
            throw new ParseException(Skill.MESSAGE_CONSTRAINTS);
        }
        return new Skill(trimmedSkill);
    }

    /**
     * Parses a {@code String skill} into a {@code FilteredSkill}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param skill the skill string to parse; must not be null
     * @return a {@code FilteredSkill} representing the parsed skill
     * @throws NullPointerException if {@code skill} is null
     * @throws ParseException if the given {@code skill} does not satisfy
     *         {@link FilteredSkill#isValidFilteredSkill(String)}
     */
    public static FilteredSkill parseFilteredSkill(String skill) throws ParseException {
        requireNonNull(skill);

        String trimmedSkill = skill.trim();
        if (!FilteredSkill.isValidFilteredSkill(trimmedSkill)) {
            throw new ParseException(FilteredSkill.MESSAGE_CONSTRAINTS);
        }
        return new FilteredSkill(trimmedSkill);
    }

    /**
     * Parses a {@code String Progress Record} into an {@code ProgressRecord}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Progress Record} is invalid.
     */
    public static ProgressRecord parseProgressRecord(String progressRecord) throws ParseException {
        requireNonNull(progressRecord);
        String trimmedProgressRecord = progressRecord.trim();
        if (!ProgressRecord.isValidProgress(trimmedProgressRecord)) {
            throw new ParseException(ProgressRecord.MESSAGE_CONSTRAINTS);
        }
        return new ProgressRecord(trimmedProgressRecord);
    }

}
