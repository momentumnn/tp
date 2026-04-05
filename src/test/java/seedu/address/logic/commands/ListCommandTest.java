package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Skill;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // shared persons used across filter tests
        model.addPerson(new PersonBuilder().withName("Beginner Bob")
                .withPhone("81234567").withSkill("Beginner").build());
        model.addPerson(new PersonBuilder().withName("Expert Eve")
                .withPhone("81234568").withSkill("Expert").build());
        model.addPerson(new PersonBuilder().withName("Intermediate Ian")
                .withPhone("81234569").withSkill("Intermediate").build());

        expectedModel.addPerson(new PersonBuilder().withName("Beginner Bob")
                .withPhone("81234567").withSkill("Beginner").build());
        expectedModel.addPerson(new PersonBuilder().withName("Expert Eve")
                .withPhone("81234568").withSkill("Expert").build());
        expectedModel.addPerson(new PersonBuilder().withName("Intermediate Ian")
                .withPhone("81234569").withSkill("Intermediate").build());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model,
                String.format(ListCommand.MESSAGE_SUCCESS, expectedModel.getFilteredPersonList().size()),
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model,
                String.format(ListCommand.MESSAGE_SUCCESS, expectedModel.getFilteredPersonList().size()),
                expectedModel);
    }

    @Test
    public void execute_listWithSkillFilterBeginner_showsFilteredList() {
        ListCommand listCommand = new ListCommand(List.of(new Skill("Beginner")));
        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Beginner"));
        assertCommandSuccess(listCommand, model,
                String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Beginner",
                        expectedModel.getFilteredPersonList().size()),
                expectedModel);
    }

    @Test
    public void execute_listWithSkillFilterIntermediate_showsFilteredList() {
        ListCommand listCommand = new ListCommand(List.of(new Skill("Intermediate")));
        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Intermediate"));
        assertCommandSuccess(listCommand, model,
                String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Intermediate",
                        expectedModel.getFilteredPersonList().size()),
                expectedModel);
    }

    @ParameterizedTest
    @ValueSource(strings = {"expert", "EXPERT", "eXpErT"})
    public void execute_listWithSkillFilterCaseInsensitive_showsFilteredList(String input) {
        ListCommand listCommand = new ListCommand(List.of(new Skill(input)));
        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Expert"));
        assertCommandSuccess(listCommand, model,
                String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Expert",
                        expectedModel.getFilteredPersonList().size()),
                expectedModel);
    }

    @Test
    public void execute_listWithMultipleSkillFilters_showsFilteredList() {
        ListCommand listCommand = new ListCommand(List.of(
                new Skill("Beginner"), new Skill("Expert")));
        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Beginner")
                        || person.getSkill().value.equalsIgnoreCase("Expert"));
        assertCommandSuccess(listCommand, model,
                String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Beginner, Expert",
                        expectedModel.getFilteredPersonList().size()),
                expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listBeginnerCommand = new ListCommand(List.of(new Skill("Beginner")));
        ListCommand listExpertCommand = new ListCommand(List.of(new Skill("Expert")));
        ListCommand listAllCommand = new ListCommand();

        // same object -> returns true
        assertTrue(listBeginnerCommand.equals(listBeginnerCommand));

        // same values -> returns true
        assertTrue(listBeginnerCommand.equals(new ListCommand(List.of(new Skill("Beginner")))));

        // different types -> returns false
        assertFalse(listBeginnerCommand.equals(1));

        // null -> returns false
        assertFalse(listBeginnerCommand.equals(null));

        // different skill -> returns false
        assertFalse(listBeginnerCommand.equals(listExpertCommand));

        // filtered vs unfiltered -> returns false
        assertFalse(listBeginnerCommand.equals(listAllCommand));

        // two unfiltered -> returns true
        assertTrue(listAllCommand.equals(new ListCommand()));

        // multiple skills same values -> returns true
        ListCommand listMultiCommand1 = new ListCommand(List.of(new Skill("Beginner"), new Skill("Expert")));
        ListCommand listMultiCommand2 = new ListCommand(List.of(new Skill("Beginner"), new Skill("Expert")));
        assertTrue(listMultiCommand1.equals(listMultiCommand2));

        // multiple skills different values -> returns false
        assertFalse(listMultiCommand1.equals(new ListCommand(List.of(
                new Skill("Beginner"), new Skill("Intermediate")))));

        // single vs multiple -> returns false
        assertFalse(listBeginnerCommand.equals(listMultiCommand1));
    }
}
