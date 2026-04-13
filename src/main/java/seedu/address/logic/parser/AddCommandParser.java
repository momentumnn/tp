package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRESS_RECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAINING_GOAL;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.InjuryStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProgressRecord;
import seedu.address.model.person.Skill;
import seedu.address.model.person.TrainingGoal;
import seedu.address.model.timeslot.Timeslot;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_INJURY_STATUS, PREFIX_TRAINING_GOAL, PREFIX_TIMESLOT,
                        PREFIX_SKILL, PREFIX_PROGRESS_RECORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TRAINING_GOAL, PREFIX_TIMESLOT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TRAINING_GOAL, PREFIX_TIMESLOT,
                PREFIX_SKILL, PREFIX_PROGRESS_RECORD, PREFIX_INJURY_STATUS);

        Name name = parseName(argMultimap);
        Phone phone = parsePhone(argMultimap);
        Email email = parseEmail(argMultimap);
        Address address = parseAddress(argMultimap);
        InjuryStatus injuryStatus = parseInjuryStatus(argMultimap);
        TrainingGoal trainingGoal = parseTrainingGoal(argMultimap);
        Set<Timeslot> timeslots = parseTimeslots(argMultimap);
        Skill skill = parseSkill(argMultimap);
        ProgressRecord progressRecord = parseProgressRecord(argMultimap);

        Person person = new Person(name, phone, email, address, injuryStatus,
            trainingGoal, timeslots, progressRecord, skill);

        return new AddCommand(person);
    }

    /**
     * Parses and returns a Name object from the argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed command arguments.
     * @return The Name object parsed from the value associated with the PREFIX_NAME.
     * @throws ParseException If the name value is invalid or missing.
     */
    private Name parseName(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
    }

    /**
     * Parses and returns a Phone object from the argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed command arguments.
     * @return The Phone object parsed from the value associated with the PREFIX_PHONE.
     * @throws ParseException If the phone value is invalid or missing.
     */
    private Phone parsePhone(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
    }

    /**
     * Parses and returns an Email object from the argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed command arguments.
     * @return The Email object parsed from the value associated with the PREFIX_EMAIL.
     * @throws ParseException If the email value is invalid or missing.
     */
    private Email parseEmail(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
    }

    /**
     * Parses and returns an Address object from the argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed command arguments.
     * @return The Address object parsed from the value associated with the PREFIX_ADDRESS.
     * @throws ParseException If the address value is invalid or missing.
     */
    private Address parseAddress(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
    }

    /**
     * Parses and returns an InjuryStatus object from the argument multimap.
     * If the injury status prefix is not present, the default injury status is used.
     *
     * @param argMultimap The argument multimap containing the parsed command arguments.
     * @return The InjuryStatus object parsed from the value associated with the PREFIX_INJURY_STATUS,
     *         or the default injury status if the prefix is absent.
     * @throws ParseException If the injury status value is present but invalid.
     */
    private InjuryStatus parseInjuryStatus(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseInjuryStatus(argMultimap.getValue(PREFIX_INJURY_STATUS)
                .orElse(InjuryStatus.DEFAULT_INJURY_STATUS));
    }

    /**
     * Parses and returns a TrainingGoal object from the argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed command arguments.
     * @return The TrainingGoal object parsed from the value associated with the PREFIX_TRAINING_GOAL.
     * @throws ParseException If the training goal value is invalid or missing.
     */
    private TrainingGoal parseTrainingGoal(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseTrainingGoal(argMultimap.getValue(PREFIX_TRAINING_GOAL).get());
    }

    /**
     * Parses and returns a set of Timeslot objects from the argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed command arguments.
     * @return A Set of Timeslot objects parsed from all values associated with the PREFIX_TIMESLOT.
     * @throws ParseException If any of the timeslot values are invalid.
     */
    private Set<Timeslot> parseTimeslots(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseTimeslots(argMultimap.getAllValues(PREFIX_TIMESLOT));
    }

    /**
     * Parses and returns a Skill object from the argument multimap.
     * The skill value is optional and may be absent.
     *
     * @param argMultimap The argument multimap containing the parsed command arguments.
     * @return The Skill object parsed from the value associated with the PREFIX_SKILL,
     *         or the default skill if the prefix is absent.
     * @throws ParseException If the skill value is present but invalid.
     */
    private Skill parseSkill(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseSkill(argMultimap.getValue(PREFIX_SKILL));
    }

    /**
     * Parses and returns a ProgressRecord object from the argument multimap.
     * If the progress record prefix is not present, the default progress record is used.
     *
     * @param argMultimap The argument multimap containing the parsed command arguments.
     * @return The ProgressRecord object parsed from the value associated with the PREFIX_PROGRESS_RECORD,
     *         or the default progress record if the prefix is absent.
     * @throws ParseException If the progress record value is present but invalid.
     */
    private ProgressRecord parseProgressRecord(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseProgressRecord(argMultimap.getValue(PREFIX_PROGRESS_RECORD)
                .orElse(ProgressRecord.DEFAULT_PROGRESS));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
