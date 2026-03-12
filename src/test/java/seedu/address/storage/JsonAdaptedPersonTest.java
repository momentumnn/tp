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
                    VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                        VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL,
            VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                        VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                        VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL,
            VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                    VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                    VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                        VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null,
            VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                        VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    INVALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                        VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, null, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                        VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidInjuryStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                INVALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = InjuryStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullInjuryStatus_returnsPersonWithDefaultInjuryStatus() throws Exception {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_TRAINING_GOAL,
                VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);

        Person expectedPerson = new PersonBuilder(BENSON)
                .withInjuryStatus(InjuryStatus.DEFAULT_INJURY_STATUS)
                .build();

        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_invalidTrainingGoal_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_INJURY_STATUS, INVALID_TRAINING_GOAL,
                VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = TrainingGoal.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTrainingGoal_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_INJURY_STATUS, null,
                    VALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TrainingGoal.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAvailability_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                    INVALID_AVAILABILITY, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = Availability.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAvailability_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                null, VALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Availability.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_missingSkill_defaultsToNovice() throws Exception {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                    VALID_AVAILABILITY, null, VALID_PROGRESS_RECORD);
        Person modelPerson = person.toModelType();
        assertEquals(new Skill(Skill.SKILL_NOVICE), modelPerson.getSkill());
    }

    @Test
    public void toModelType_invalidSkill_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                        VALID_AVAILABILITY, INVALID_SKILL, VALID_PROGRESS_RECORD);
        String expectedMessage = Skill.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidProgress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_INJURY_STATUS, VALID_TRAINING_GOAL,
                    VALID_AVAILABILITY, VALID_SKILL, INVALID_PROGRESS_RECORD);
        String expectedMessage = ProgressRecord.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
