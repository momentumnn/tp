package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FilteredSkill;
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
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listWithSkillFilterBeginner_showsFilteredList() {
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

        ListCommand listCommand = new ListCommand(List.of(new FilteredSkill("Beginner")));

        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Beginner"));

        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Beginner");
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listWithSkillFilterIntermediate_showsFilteredList() {
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

        ListCommand listCommand = new ListCommand(List.of(new FilteredSkill("Intermediate")));

        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Intermediate"));

        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Intermediate");
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listWithSkillFilterCaseInsensitive_showsFilteredList() {
        model.addPerson(new PersonBuilder().withName("Expert Eve").withSkill("Expert").build());
        expectedModel.addPerson(new PersonBuilder().withName("Expert Eve").withSkill("Expert").build());

        ListCommand listCommand = new ListCommand(List.of(new FilteredSkill("eXpErT")));

        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Expert"));

        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Expert");
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listWithSkillFilterNoMatches_showsEmptyList() {
        ListCommand listCommand = new ListCommand(List.of(new FilteredSkill("Advanced")));

        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Advanced"));

        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Advanced");
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listWithMultipleSkillFilters_showsFilteredList() {
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

        ListCommand listCommand = new ListCommand(List.of(
                new FilteredSkill("Beginner"), new FilteredSkill("Expert")));

        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Beginner")
                        || person.getSkill().value.equalsIgnoreCase("Expert"));

        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Beginner, Expert");
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleSkillFiltersNoMatches_showsEmptyList() {
        ListCommand listCommand = new ListCommand(List.of(
                new FilteredSkill("Advanced"), new FilteredSkill("Rookie")));

        expectedModel.updateFilteredPersonList(person ->
                person.getSkill().value.equalsIgnoreCase("Advanced")
                        || person.getSkill().value.equalsIgnoreCase("Rookie"));

        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Advanced, Rookie");
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredList_correctMessageReturned() throws Exception {
        ListCommand command = new ListCommand();
        CommandResult result = command.execute(model);
        assertEquals(ListCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_filteredList_correctMessageReturned() throws Exception {
        ListCommand command = new ListCommand(List.of(new FilteredSkill("Beginner")));
        CommandResult result = command.execute(model);
        assertEquals(String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Beginner"),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleSkillFilters_correctMessageReturned() throws Exception {
        ListCommand command = new ListCommand(List.of(
                new FilteredSkill("Beginner"), new FilteredSkill("Expert")));
        CommandResult result = command.execute(model);
        assertEquals(String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, "Beginner, Expert"),
                result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ListCommand listBeginnerCommand = new ListCommand(List.of(new FilteredSkill("Beginner")));
        ListCommand listExpertCommand = new ListCommand(List.of(new FilteredSkill("Expert")));
        ListCommand listAllCommand = new ListCommand();

        // same object -> returns true
        assertTrue(listBeginnerCommand.equals(listBeginnerCommand));

        // same values -> returns true
        ListCommand listBeginnerCommandCopy = new ListCommand(List.of(new FilteredSkill("Beginner")));
        assertTrue(listBeginnerCommand.equals(listBeginnerCommandCopy));

        // different types -> returns false
        assertFalse(listBeginnerCommand.equals(1));

        // null -> returns false
        assertFalse(listBeginnerCommand.equals(null));

        // different skill filter -> returns false
        assertFalse(listBeginnerCommand.equals(listExpertCommand));

        // filtered vs unfiltered -> returns false
        assertFalse(listBeginnerCommand.equals(listAllCommand));

        // two unfiltered commands -> returns true
        ListCommand anotherListAllCommand = new ListCommand();
        assertTrue(listAllCommand.equals(anotherListAllCommand));

        // multiple skills same values -> returns true
        ListCommand listMultiCommand1 = new ListCommand(List.of(
                new FilteredSkill("Beginner"), new FilteredSkill("Expert")));
        ListCommand listMultiCommand2 = new ListCommand(List.of(
                new FilteredSkill("Beginner"), new FilteredSkill("Expert")));
        assertTrue(listMultiCommand1.equals(listMultiCommand2));

        // multiple skills different values -> returns false
        ListCommand listMultiCommand3 = new ListCommand(List.of(
                new FilteredSkill("Beginner"), new FilteredSkill("Intermediate")));
        assertFalse(listMultiCommand1.equals(listMultiCommand3));

        // single vs multiple skills -> returns false
        assertFalse(listBeginnerCommand.equals(listMultiCommand1));
    }
}
