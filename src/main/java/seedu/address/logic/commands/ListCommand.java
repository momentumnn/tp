package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.FilteredSkill;

/**
 * Lists all persons in the address book to the user.
 * Can optionally filter by skill level using the {@code s/} prefix.
 *
 * <p>If no skill filter is provided, all persons are listed.
 * If a skill filter is provided, only persons whose skill level matches
 * the given filter (case-insensitive) are listed.
 *
 * <p>Command format: {@code list [s/SKILL]}
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_SUCCESS_FILTERED = "Listed all persons with skill level: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all persons or filters by skill level.\n"
            + "Parameters: [s/SKILL]\n"
            + "Example: " + COMMAND_WORD + " s/beginner";

    private final List<FilteredSkill> skills;

    /**
     * Creates a {@code ListCommand} that lists all persons without any filter.
     */
    public ListCommand() {
        this.skills = List.of();
    }

    /**
     * Creates a {@code ListCommand} that lists only persons whose skill level
     * matches any of the given {@code skillLevels}.
     *
     * @param skillLevels the skill levels to filter by; must not be null or empty
     * @throws NullPointerException if {@code skillLevels} is null
     */
    public ListCommand(List<FilteredSkill> skillLevels) {
        requireNonNull(skillLevels);
        this.skills = skillLevels;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (skills.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.updateFilteredPersonList(person ->
                    skills.stream().anyMatch(skill ->
                            person.getSkill().value.equalsIgnoreCase(skill.value)));
            String skillNames = skills.stream()
                    .map(s -> s.value)
                    .collect(java.util.stream.Collectors.joining(", "));
            return new CommandResult(String.format(MESSAGE_SUCCESS_FILTERED, skillNames));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListCommand)) {
            return false;
        }
        ListCommand otherListCommand = (ListCommand) other;
        return skills.equals(otherListCommand.skills);
    }
}
