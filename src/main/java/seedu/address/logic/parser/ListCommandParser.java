//@@author bingmybongg
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Skill;

/**
 * Parses input arguments and creates a new {@code ListCommand} object.
 *
 * <p>Supports an optional skill filter prefix ({@code s/}) to list only persons
 * with a matching skill level. If no prefix is provided, a {@code ListCommand}
 * that lists all persons is returned.
 *
 * <p>If multiple {@code s/} prefixes are provided, all values are collected and
 * a {@code ListCommand} that lists persons matching any of the specified skill
 * levels is returned.
 *
 * <p>Examples:
 * <ul>
 *   <li>{@code list} — lists all persons</li>
 *   <li>{@code list s/beginner} — lists persons with beginner skill level</li>
 *   <li>{@code list s/beginner s/expert} — lists persons with beginner or expert skill level</li>
 * </ul>
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ListCommand}
     * and returns a {@code ListCommand} object for execution.
     *
     * <p>If the skill prefix ({@code s/}) is present, the argument is parsed into a
     * {@code FilteredSkill} and a filtered {@code ListCommand} is returned.
     * Otherwise, an unfiltered {@code ListCommand} is returned.
     *
     * <p>If multiple skill prefixes are provided, it will list all persons with any of the mentioned skills.
     *
     * @param args the raw argument string provided by the user; must not be null
     * @return a {@code ListCommand} with or without a skill filter
     * @throws ParseException if the user input does not conform to the expected format,
     *         or if the skill value is invalid
     */
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SKILL);

        // Check for any tokens that are not the skill prefix by re-tokenizing
        // against all known prefixes and checking if any unknown ones have values
        ArgumentMultimap allPrefixesMap =
                ArgumentTokenizer.tokenize(args, PREFIX_SKILL, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TIMESLOT,
                        PREFIX_TRAINING_GOAL, PREFIX_PROGRESS_RECORD, PREFIX_INJURY_STATUS);

        if (allPrefixesMap.getValue(PREFIX_NAME).isPresent()
                || allPrefixesMap.getValue(PREFIX_PHONE).isPresent()
                || allPrefixesMap.getValue(PREFIX_EMAIL).isPresent()
                || allPrefixesMap.getValue(PREFIX_ADDRESS).isPresent()
                || allPrefixesMap.getValue(PREFIX_TRAINING_GOAL).isPresent()
                || !allPrefixesMap.getAllValues(PREFIX_TIMESLOT).isEmpty()
                || allPrefixesMap.getValue(PREFIX_PROGRESS_RECORD).isPresent()
                || allPrefixesMap.getValue(PREFIX_INJURY_STATUS).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        List<String> skillValues = argMultimap.getAllValues(PREFIX_SKILL);

        List<Skill> skills = new ArrayList<>();
        for (String skillValue : skillValues) {
            skills.add(ParserUtil.parseSkill(Optional.of(skillValue)));
        }
        return new ListCommand(skills);
    }
}
