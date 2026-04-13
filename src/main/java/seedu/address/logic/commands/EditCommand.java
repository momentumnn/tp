package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRESS_RECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAINING_GOAL;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TIMESLOT + "TIMESLOTS] "
            + "[" + PREFIX_TRAINING_GOAL + "TRAINING GOAL] "
            + "[" + PREFIX_PROGRESS_RECORD + "PROGRESS_RECORD] "
            + "[" + PREFIX_INJURY_STATUS + "INJURY_STATUS] "
            + "[" + PREFIX_SKILL + "SKILL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NO_CHANGES_MADE = "The provided field(s) is/are the same as the current person. "
            + "No changes were made.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (personToEdit.equals(editedPerson)) {
            throw new CommandException(MESSAGE_NO_CHANGES_MADE);
        }

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        InjuryStatus updatedInjuryStatus = editPersonDescriptor.getInjuryStatus()
            .orElse(personToEdit.getInjuryStatus());
        TrainingGoal updatedTrainingGoal = editPersonDescriptor.getTrainingGoal()
                                            .orElse(personToEdit.getTrainingGoal());
        Set<Timeslot> updatedTimeslots = editPersonDescriptor.getTimeslots()
                .orElse(personToEdit.getTimeslots());
        ProgressRecord updatedProgressRecord = editPersonDescriptor.getProgressRecord()
                .orElse(personToEdit.getProgressRecord());
        Skill updatedSkill = editPersonDescriptor.getSkill()
                .orElse(personToEdit.getSkill());

        return new Person(updatedName, updatedPhone, updatedEmail,
                updatedAddress, updatedInjuryStatus, updatedTrainingGoal, updatedTimeslots,
                updatedProgressRecord, updatedSkill);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private InjuryStatus injuryStatus;
        private TrainingGoal trainingGoal;
        private Set<Timeslot> timeslots;
        private ProgressRecord progressRecord;
        private Skill skill;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code trainingGoal} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setInjuryStatus(toCopy.injuryStatus);
            setTrainingGoal(toCopy.trainingGoal);
            setTimeslots(toCopy.timeslots);
            setProgressRecord(toCopy.progressRecord);
            setSkill(toCopy.skill);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, injuryStatus,
                    trainingGoal, timeslots, progressRecord, skill);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public void setInjuryStatus(InjuryStatus injuryStatus) {
            this.injuryStatus = injuryStatus;
        }

        public Optional<InjuryStatus> getInjuryStatus() {
            return Optional.ofNullable(injuryStatus);
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setTrainingGoal(TrainingGoal trainingGoal) {
            this.trainingGoal = trainingGoal;
        }

        public Optional<TrainingGoal> getTrainingGoal() {
            return Optional.ofNullable(trainingGoal);
        }

        /**
         * Sets {@code timeslots} to this object's {@code timeslots}.
         * A defensive copy of {@code timeslots} is used internally.
         */
        public void setTimeslots(Set<Timeslot> timeslots) {
            this.timeslots = (timeslots != null) ? new TreeSet<>(timeslots) : null;
        }

        /**
         * Returns an unmodifiable timeslot set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code timeslot} is null.
         */
        public Optional<Set<Timeslot>> getTimeslots() {
            return (timeslots != null) ? Optional.of(Collections.unmodifiableSet(timeslots)) : Optional.empty();
        }

        public void setProgressRecord(ProgressRecord progressRecord) {
            this.progressRecord = progressRecord;
        }

        public Optional<ProgressRecord> getProgressRecord() {
            return Optional.ofNullable(progressRecord);
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
        }

        public Optional<Skill> getSkill() {
            return Optional.ofNullable(skill);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(injuryStatus, otherEditPersonDescriptor.injuryStatus)
                    && Objects.equals(trainingGoal, otherEditPersonDescriptor.trainingGoal)
                    && Objects.equals(timeslots, otherEditPersonDescriptor.timeslots)
                    && Objects.equals(progressRecord, otherEditPersonDescriptor.progressRecord)
                    && Objects.equals(skill, otherEditPersonDescriptor.skill);
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
                    .add("timeslots", timeslots)
                    .add("progressRecord", progressRecord)
                    .add("skill", skill)
                    .toString();
        }

    }
}
