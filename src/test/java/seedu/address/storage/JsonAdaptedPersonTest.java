package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
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
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedPersonTest {
    private static final String INVALID_INJURY_STATUS = " ";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TIMESLOT = "MON:13";
    private static final String DUPLICATE_DAY_TIMESLOTS_MESSAGE =
            "Invalid value for Timeslot: \"mon:0900-1000\". Timeslot should have only one entry per day.";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TRAINING_GOAL = " ";
    private static final String INVALID_SKILL = "master";
    private static final String INVALID_PROGRESS_RECORD = "123";
    private static final String VALID_MULTI_SLOT_TIMESLOT = "mon:0800-0900,0900-1000";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SKILL = BENSON.getSkill().toString();
    private static final String VALID_TRAINING_GOAL = BENSON.getTrainingGoal().toString();
    private static final String VALID_PROGRESS_RECORD = BENSON.getProgressRecord().toString();
    private static final String VALID_INJURY_STATUS = BENSON.getInjuryStatus().toString();
    private static final List<String> VALID_TIMESLOTS = BENSON.getTimeslots().stream()
            .map(Timeslot::toStorageString)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        // EP: all fields valid
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        // EP: name invalid format
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for Name: \"" + INVALID_NAME
                + "\". " + Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        // EP: name missing
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        // EP: phone invalid format
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for Phone: \"" + INVALID_PHONE
                + "\". " + Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        // EP: phone missing
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        // EP: email invalid format
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for Email: \"" + INVALID_EMAIL
                + "\". " + Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        // EP: email missing
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        // EP: address invalid format
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for Address: \"" + INVALID_ADDRESS
                + "\". " + Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        // EP: address missing
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_emptyTimeslots_throwsIllegalValueException() {
        // EP: timeslots empty
        // Boundary value: zero entries
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, new ArrayList<>(), VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timeslot.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTimeslots_throwsIllegalValueException() {
        // EP: timeslots missing
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, null, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timeslot.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTimeslots_throwsIllegalValueException() {
        // EP: timeslots contain invalid entry
        List<String> invalidTimeslots = new ArrayList<>(VALID_TIMESLOTS);
        invalidTimeslots.add(INVALID_TIMESLOT);
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, invalidTimeslots, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for Timeslot: \"" + INVALID_TIMESLOT
                + "\". " + Timeslot.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTimeslot_throwsIllegalValueException() {
        // EP: timeslots contain null entry
        List<String> invalidTimeslots = new ArrayList<>(VALID_TIMESLOTS);
        invalidTimeslots.add(null);
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, invalidTimeslots, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for Timeslot: \"null\". " + Timeslot.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_validMultiSlotTimeslot_returnsPerson() throws Exception {
        // EP: valid multi-slot timeslot in one entry
        List<String> timeslots = new ArrayList<>();
        timeslots.add(VALID_MULTI_SLOT_TIMESLOT);
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, timeslots, VALID_SKILL, VALID_PROGRESS_RECORD);
        Person expectedPerson = new PersonBuilder(BENSON)
                .withTimeslots(VALID_MULTI_SLOT_TIMESLOT)
                .build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_lowerCaseTimeslot_returnsPerson() throws Exception {
        // EP: valid timeslot with lowercase day
        List<String> timeslots = new ArrayList<>();
        timeslots.add("mon:0800-0900");

        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, timeslots, VALID_SKILL, VALID_PROGRESS_RECORD);

        Person expectedPerson = new PersonBuilder(BENSON)
                .withTimeslots("mon:0800-0900")
                .build();

        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_duplicateDayTimeslots_throwsIllegalValueException() {
        // EP: timeslots contain duplicate day
        List<String> invalidTimeslots = new ArrayList<>();
        invalidTimeslots.add("mon:0800-0900");
        invalidTimeslots.add("mon:0900-1000");
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, invalidTimeslots, VALID_SKILL, VALID_PROGRESS_RECORD);
        assertThrows(IllegalValueException.class, DUPLICATE_DAY_TIMESLOTS_MESSAGE, person::toModelType);
    }

    @Test
    public void toModelType_duplicateDayTimeslotsDifferentCase_throwsIllegalValueException() {
        // EP: timeslots contain duplicate day with different casing
        List<String> invalidTimeslots = new ArrayList<>();
        invalidTimeslots.add("MON:0800-0900");
        invalidTimeslots.add("mon:0900-1000");

        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, invalidTimeslots, VALID_SKILL, VALID_PROGRESS_RECORD);

        assertThrows(IllegalValueException.class, DUPLICATE_DAY_TIMESLOTS_MESSAGE, person::toModelType);
    }

    @Test
    public void toModelType_invalidTrainingGoal_throwsIllegalValueException() {
        // EP: training goal invalid format
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        INVALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for TrainingGoal: \"" + INVALID_TRAINING_GOAL
                + "\". " + TrainingGoal.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTrainingGoal_throwsIllegalValueException() {
        // EP: training goal missing
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        null, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TrainingGoal.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidProgress_throwsIllegalValueException() {
        // EP: progress record invalid format
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, INVALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for ProgressRecord: \"" + INVALID_PROGRESS_RECORD
                + "\". " + ProgressRecord.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullProgress_setsDefaultProgress() throws Exception {
        // EP: progress record missing
        // Expected behavior: default applied
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, null);
        Person expectedPerson = new PersonBuilder(BENSON)
                .withProgressRecord(ProgressRecord.DEFAULT_PROGRESS)
                .build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_invalidInjuryStatus_throwsIllegalValueException() {
        // EP: injury status invalid format
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for InjuryStatus: \"" + INVALID_INJURY_STATUS
                + "\". " + InjuryStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullInjuryStatus_setsDefaultInjuryStatus() throws Exception {
        // EP: injury status missing
        // Expected behavior: default applied
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, VALID_SKILL, VALID_PROGRESS_RECORD);
        Person expectedPerson = new PersonBuilder(BENSON)
                .withInjuryStatus(InjuryStatus.DEFAULT_INJURY_STATUS)
                .build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_nullSkill_setsDefaultSkill() throws Exception {
        // EP: skill missing
        // Expected behavior: default applied
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, null, VALID_PROGRESS_RECORD);
        Person modelPerson = person.toModelType();
        assertEquals(new Skill(Skill.SKILL_BEGINNER), modelPerson.getSkill());
    }

    @Test
    public void toModelType_invalidSkill_throwsIllegalValueException() {
        // EP: skill invalid value
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_INJURY_STATUS,
                        VALID_TRAINING_GOAL, VALID_TIMESLOTS, INVALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = "Invalid value for Skill: \"" + INVALID_SKILL
                + "\". " + Skill.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
