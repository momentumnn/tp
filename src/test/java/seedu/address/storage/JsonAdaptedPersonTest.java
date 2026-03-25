package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Availability;
import seedu.address.model.person.Email;
import seedu.address.model.person.InjuryStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProgressRecord;
import seedu.address.model.person.Skill;
import seedu.address.model.person.TrainingGoal;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedPersonTest {
    private static final String INVALID_INJURY_STATUS = " ";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_AVAILABILITY = "MON: 1000-1100";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TRAINING_GOAL = " ";
    private static final String INVALID_SKILL = "master";
    private static final String INVALID_PROGRESS_RECORD = "123";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SKILL = BENSON.getSkill().toString();
    private static final String VALID_TRAINING_GOAL = BENSON.getTrainingGoal().toString();
    private static final String VALID_AVAILABILITY = BENSON.getAvailability().toString();
    private static final String VALID_PROGRESS_RECORD = BENSON.getProgressRecord().toString();
    private static final String VALID_INJURY_STATUS = BENSON.getInjuryStatus().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                        VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = "Invalid value for Name: \"" + INVALID_NAME
                + "\". Expected: " + Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL,
            VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                        VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                        VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = "Invalid value for Phone: \"" + INVALID_PHONE
                + "\". Expected: " + Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL,
            VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                    VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                    VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                        VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = "Invalid value for Email: \"" + INVALID_EMAIL
                + "\". Expected: " + Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null,
            VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                        VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    INVALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                        VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = "Invalid value for Address: \"" + INVALID_ADDRESS
                + "\". Expected: " + Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
            JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, null, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                        VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAvailability_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, INVALID_AVAILABILITY, VALID_TRAINING_GOAL,
                    VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = "Invalid value for Availability: \"" + INVALID_AVAILABILITY
                + "\". Expected: " + Availability.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAvailability_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_ADDRESS, null, VALID_TRAINING_GOAL,
                        VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Availability.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTrainingGoal_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_AVAILABILITY, INVALID_TRAINING_GOAL,
                VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = "Invalid value for TrainingGoal: \"" + INVALID_TRAINING_GOAL
                + "\". Expected: " + TrainingGoal.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidProgress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                    INVALID_PROGRESS_RECORD, VALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = "Invalid value for ProgressRecord: \"" + INVALID_PROGRESS_RECORD
                + "\". Expected: " + ProgressRecord.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullProgress_setsDefaultProgress() throws Exception {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                    null, VALID_INJURY_STATUS, VALID_SKILL);
        Person expectedPerson = new PersonBuilder(BENSON)
                .withProgressRecord(ProgressRecord.DEFAULT_PROGRESS)
                .build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_invalidInjuryStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                VALID_PROGRESS_RECORD, INVALID_INJURY_STATUS, VALID_SKILL);
        String expectedMessage = "Invalid value for InjuryStatus: \"" + INVALID_INJURY_STATUS
                + "\". Expected: " + InjuryStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullInjuryStatus_setsDefaultInjuryStatus() throws Exception {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                VALID_PROGRESS_RECORD, null, VALID_SKILL);
        Person expectedPerson = new PersonBuilder(BENSON)
                .withInjuryStatus(InjuryStatus.DEFAULT_INJURY_STATUS)
                .build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_nullSkill_setsDefaultSkill() throws Exception {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                    VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, null);
        Person modelPerson = person.toModelType();
        assertEquals(new Skill(Skill.SKILL_BEGINNER), modelPerson.getSkill());
    }

    @Test
    public void toModelType_invalidSkill_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_AVAILABILITY, VALID_TRAINING_GOAL,
                        VALID_PROGRESS_RECORD, VALID_INJURY_STATUS, INVALID_SKILL);
        String expectedMessage = "Invalid value for Skill: \"" + INVALID_SKILL
                + "\". Expected: " + Skill.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
