package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private final List<String> timeslots = new ArrayList<>();
    private String progressRecord;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("injuryStatus") String injuryStatus,
            @JsonProperty("trainingGoal") String trainingGoal,
            @JsonProperty("timeslots") List<String> timeslots,
            @JsonProperty("skill") String skill, @JsonProperty("progressRecord") String progressRecord) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.injuryStatus = injuryStatus;
        this.trainingGoal = trainingGoal;
        if (timeslots != null) {
            this.timeslots.addAll(timeslots);
        }
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
        timeslots.addAll(source.getTimeslots().stream()
                .map(Timeslot::toStorageString)
                .collect(Collectors.toList()));
        skill = source.getSkill().value;
        progressRecord = source.getProgressRecord().value;
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
        TrainingGoal modelTrainingGoal = toModelTrainingGoal();
        ProgressRecord modelProgressRecord = toModelProgressRecord();
        InjuryStatus modelInjuryStatus = toModelInjuryStatus();
        Skill modelSkill = toModelSkill();
        final Set<Timeslot> modelTimeslots = toModelTimeslotSet();

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelInjuryStatus, modelTrainingGoal,
                modelTimeslots, modelProgressRecord, modelSkill);
    }

    /**
     * Converts the stored name string to a model {@code Name} after validating format.
     *
     * @throws IllegalValueException if name is missing or invalid.
     */
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

    /**
     * Converts the stored phone string to a model {@code Phone} after validating format.
     *
     * @throws IllegalValueException if phone is missing or invalid.
     */
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

    /**
     * Converts the stored email string to a model {@code Email} after validating format.
     *
     * @throws IllegalValueException if email is missing or invalid.
     */
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

    /**
     * Converts the stored address string to a model {@code Address} validating format.
     *
     * @throws IllegalValueException if address is missing or invalid.
     */
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

    /**
     * Converts the stored training goal string to a model {@code TrainingGoal} validating format.
     *
     * @throws IllegalValueException if training goal is missing or invalid.
     */
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

    /**
     * Converts the stored progress record string to a model {@code ProgressRecord}, default if absent.
     *
     * @throws IllegalValueException if the derived progress value is invalid.
     */
    private ProgressRecord toModelProgressRecord() throws IllegalValueException {
        String record = progressRecord == null ? ProgressRecord.DEFAULT_PROGRESS : progressRecord;
        if (!ProgressRecord.isValidProgress(record)) {
            throw new IllegalValueException(formatInvalidFieldMessage(ProgressRecord.class.getSimpleName(),
                    ProgressRecord.MESSAGE_CONSTRAINTS, record));
        }
        return new ProgressRecord(record);
    }

    /**
     * Converts the stored injury status string to a model {@code InjuryStatus}, default if absent.
     *
     * @throws IllegalValueException if the stored injury status is invalid.
     */
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

    /**
     * Converts the stored skill string to a model {@code Skill}, default if absent.
     *
     * @throws IllegalValueException if the stored skill is invalid.
     */
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

    /**
     * Converts the stored timeslot strings into a validated, sorted {@code Set<Timeslot>}.
     *
     * @throws IllegalValueException if any stored timeslot string is missing or invalid.
     */
    private Set<Timeslot> toModelTimeslotSet() throws IllegalValueException {
        if (timeslots.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Timeslot.class.getSimpleName()));
        }
        List<Timeslot> personTimeslots = new ArrayList<>();
        for (String timeslot : timeslots) {
            personTimeslots.add(toModelTimeslot(timeslot));
        }
        return new TreeSet<>(personTimeslots);
    }

    /**
     * Converts the stored timeslot string to a model {@code Timeslot}, default if absent.
     *
     * @throws IllegalValueException if the stored timeslot is invalid.
     */
    private Timeslot toModelTimeslot(String timeslot) throws IllegalValueException {
        if (timeslot == null) {
            throw new IllegalValueException(formatInvalidFieldMessage(Timeslot.class.getSimpleName(),
                    Timeslot.MESSAGE_CONSTRAINTS, "null"));
        }
        if (!Timeslot.isValidTimeslot(timeslot)) {
            throw new IllegalValueException(formatInvalidFieldMessage(Timeslot.class.getSimpleName(),
                    Timeslot.MESSAGE_CONSTRAINTS, timeslot));
        }
        return new Timeslot(timeslot);
    }

    /**
     * Formats a constraint failure message that includes the offending field and value.
     */
    private static String formatInvalidFieldMessage(String fieldName, String expected, String actualValue) {
        return String.format("Invalid value for %s: \"%s\". %s", fieldName, actualValue, expected);
    }

}
