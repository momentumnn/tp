package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final InjuryStatus injuryStatus;
    private final TrainingGoal trainingGoal;
    private final Availability availability;
    private final Skill skill;
    private final ProgressRecord progressRecord;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
            InjuryStatus injuryStatus, TrainingGoal trainingGoal,
            Availability availability, Skill skill, ProgressRecord progressRecord) {
        requireAllNonNull(name, phone, email, address, trainingGoal, availability, skill);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.trainingGoal = trainingGoal;
        this.availability = availability;
        this.skill = skill;
        this.progressRecord = progressRecord;
        this.injuryStatus = injuryStatus;
    }

    /**
     * Creates a {@code Person} without an explicit skill level.
     * The skill level defaults to novice.
     *
     * This constructor is provided to support edit command.
     */
    public Person(Name name, Phone phone, Email email, Address address,
            InjuryStatus injuryStatus, TrainingGoal trainingGoal,
            Availability availability, ProgressRecord progressRecord) {
        requireAllNonNull(name, phone, email, address, injuryStatus, trainingGoal, availability);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.injuryStatus = injuryStatus;
        this.trainingGoal = trainingGoal;
        this.availability = availability;
        this.skill = new Skill(Skill.SKILL_NOVICE);
        this.progressRecord = progressRecord;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public InjuryStatus getInjuryStatus() {
        return injuryStatus;
    }

    public TrainingGoal getTrainingGoal() {
        return trainingGoal;
    }

    public Availability getAvailability() {
        return availability;
    }

    public Skill getSkill() {
        return skill;
    }

    public ProgressRecord getProgressRecord() {
        return progressRecord;
    }


    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && injuryStatus.equals(otherPerson.injuryStatus)
                && trainingGoal.equals(otherPerson.trainingGoal)
                && availability.equals(otherPerson.availability)
                && skill.equals(otherPerson.skill)
                && progressRecord.equals(otherPerson.progressRecord);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, injuryStatus,
            trainingGoal, availability, skill, progressRecord);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("injuryStatus", injuryStatus)
                .add("trainingGoal", trainingGoal)
                .add("availability", availability)
                .add("skill", skill)
                .add("progressRecord", progressRecord)
                .toString();
    }

}
