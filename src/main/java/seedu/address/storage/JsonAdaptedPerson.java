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
    private final String injuryStatus;
    private final String skill;
    private final String trainingGoal;
    private final String availability;
    private String progressRecord;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("injuryStatus") String injuryStatus,
            @JsonProperty("trainingGoal") String trainingGoal, @JsonProperty("availability") String availability,
            @JsonProperty("skill") String skill, @JsonProperty("progressRecord") String progressRecord) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.injuryStatus = injuryStatus;
        this.trainingGoal = trainingGoal;
        this.availability = availability;
        this.skill = skill;
        this.progressRecord = progressRecord;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        injuryStatus = source.getInjuryStatus().value;
        trainingGoal = source.getTrainingGoal().value;
        availability = source.getAvailability().value;
        skill = source.getSkill().value;
        progressRecord = source.getProgressRecord().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final InjuryStatus modelInjuryStatus;

        if (injuryStatus == null) {
            modelInjuryStatus = new InjuryStatus(InjuryStatus.DEFAULT_INJURY_STATUS);
        } else if (!InjuryStatus.isValidInjuryStatus(injuryStatus)) {
            throw new IllegalValueException(InjuryStatus.MESSAGE_CONSTRAINTS);
        } else {
            modelInjuryStatus = new InjuryStatus(injuryStatus);
        }

        if (trainingGoal == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                            TrainingGoal.class.getSimpleName()));
        }
        if (!TrainingGoal.isValidTrainingGoal(trainingGoal)) {
            throw new IllegalValueException(TrainingGoal.MESSAGE_CONSTRAINTS);
        }

        final TrainingGoal modelTrainingGoal = new TrainingGoal(trainingGoal);
        if (availability == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Availability.class.getSimpleName()));
        }
        if (!Availability.isValidAvailability(availability)) {
            throw new IllegalValueException(Availability.MESSAGE_CONSTRAINTS);
        }
        final Availability modelAvailability = new Availability(availability);

        final Skill modelSkill;
        if (skill == null) {
            modelSkill = new Skill(Skill.SKILL_NOVICE);
        } else if (!Skill.isValidSkill(skill)) {
            throw new IllegalValueException(Skill.MESSAGE_CONSTRAINTS);
        } else {
            modelSkill = new Skill(skill);
        }

        if (progressRecord == null) {
            progressRecord = "0%";
        }
        if (!ProgressRecord.isValidProgress(progressRecord)) {
            throw new IllegalValueException(ProgressRecord.MESSAGE_CONSTRAINTS);
        }
        final ProgressRecord modelProgressRecord = new ProgressRecord(progressRecord);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelInjuryStatus, modelTrainingGoal,
                modelAvailability, modelSkill, modelProgressRecord);
    }

}
