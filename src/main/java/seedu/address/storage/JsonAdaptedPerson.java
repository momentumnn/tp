package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String availability;
    private final String trainingGoal;
    private String progressRecord;
    private final String injuryStatus;
    private final String skill;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("availability") String availability, @JsonProperty("trainingGoal") String trainingGoal,
            @JsonProperty("progressRecord") String progressRecord, @JsonProperty("injuryStatus") String injuryStatus,
            @JsonProperty("skill") String skill) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.availability = availability;
        this.trainingGoal = trainingGoal;
        this.progressRecord = progressRecord;
        this.injuryStatus = injuryStatus;
        this.skill = skill;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        availability = source.getAvailability().value;
        trainingGoal = source.getTrainingGoal().value;
        progressRecord = source.getProgressRecord().value;
        injuryStatus = source.getInjuryStatus().value;
        skill = source.getSkill().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        Name modelName = toModelName();
        Phone modelPhone = toModelPhone();
        Email modelEmail = toModelEmail();
        Address modelAddress = toModelAddress();
        Availability modelAvailability = toModelAvailability();
        TrainingGoal modelTrainingGoal = toModelTrainingGoal();
        ProgressRecord modelProgressRecord = toModelProgressRecord();
        InjuryStatus modelInjuryStatus = toModelInjuryStatus();
        Skill modelSkill = toModelSkill();

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelInjuryStatus, modelTrainingGoal,
                modelAvailability, modelProgressRecord, modelSkill);
    }

    private Name toModelName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(formatInvalidFieldMessage(Name.class.getSimpleName(),
                    Name.MESSAGE_CONSTRAINTS, name));
        }
        return new Name(name);
    }

    private Phone toModelPhone() throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(formatInvalidFieldMessage(Phone.class.getSimpleName(),
                    Phone.MESSAGE_CONSTRAINTS, phone));
        }
        return new Phone(phone);
    }

    private Email toModelEmail() throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(formatInvalidFieldMessage(Email.class.getSimpleName(),
                    Email.MESSAGE_CONSTRAINTS, email));
        }
        return new Email(email);
    }

    private Address toModelAddress() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(formatInvalidFieldMessage(Address.class.getSimpleName(),
                    Address.MESSAGE_CONSTRAINTS, address));
        }
        return new Address(address);
    }

    private Availability toModelAvailability() throws IllegalValueException {
        if (availability == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Availability.class.getSimpleName()));
        }
        if (!Availability.isValidAvailability(availability)) {
            throw new IllegalValueException(formatInvalidFieldMessage(Availability.class.getSimpleName(),
                    Availability.MESSAGE_CONSTRAINTS, availability));
        }
        return new Availability(availability);
    }

    private TrainingGoal toModelTrainingGoal() throws IllegalValueException {
        if (trainingGoal == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TrainingGoal.class.getSimpleName()));
        }
        if (!TrainingGoal.isValidTrainingGoal(trainingGoal)) {
            throw new IllegalValueException(formatInvalidFieldMessage(TrainingGoal.class.getSimpleName(),
                    TrainingGoal.MESSAGE_CONSTRAINTS, trainingGoal));
        }
        return new TrainingGoal(trainingGoal);
    }

    private ProgressRecord toModelProgressRecord() throws IllegalValueException {
        String record = progressRecord == null ? ProgressRecord.DEFAULT_PROGRESS : progressRecord;
        if (!ProgressRecord.isValidProgress(record)) {
            throw new IllegalValueException(formatInvalidFieldMessage(ProgressRecord.class.getSimpleName(),
                    ProgressRecord.MESSAGE_CONSTRAINTS, record));
        }
        return new ProgressRecord(record);
    }

    private InjuryStatus toModelInjuryStatus() throws IllegalValueException {
        if (injuryStatus == null) {
            return new InjuryStatus(InjuryStatus.DEFAULT_INJURY_STATUS);
        }
        if (!InjuryStatus.isValidInjuryStatus(injuryStatus)) {
            throw new IllegalValueException(formatInvalidFieldMessage(InjuryStatus.class.getSimpleName(),
                    InjuryStatus.MESSAGE_CONSTRAINTS, injuryStatus));
        }
        return new InjuryStatus(injuryStatus);
    }

    private Skill toModelSkill() throws IllegalValueException {
        if (skill == null) {
            return new Skill(Skill.SKILL_BEGINNER);
        }
        if (!Skill.isValidSkill(skill)) {
            throw new IllegalValueException(formatInvalidFieldMessage(Skill.class.getSimpleName(),
                    Skill.MESSAGE_CONSTRAINTS, skill));
        }
        return new Skill(skill);
    }

    private static String formatInvalidFieldMessage(String fieldName, String expected, String actualValue) {
        return String.format("Invalid value for %s: \"%s\". Expected: %s", fieldName, actualValue, expected);
    }

}
