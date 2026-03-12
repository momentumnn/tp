package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Availability;
import seedu.address.model.person.Email;
import seedu.address.model.person.InjuryStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProgressRecord;
import seedu.address.model.person.TrainingGoal;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new InjuryStatus("Healthy"),
                new TrainingGoal("1000 pushups"),
                new Availability("mon:0900-1000,tue:1000-1100,wed:1700-1800,fri:1800-1900"),
                new ProgressRecord("100%")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new InjuryStatus("Ankle Sprain"),
                new TrainingGoal("10 km run no sweat"),
                new Availability("mon:0900-1000,tue:0900-1000,wed:1500-1600,sat:0700-0900"),
                new ProgressRecord("10%")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new InjuryStatus("Recovering"),
                new TrainingGoal("6 packs"),
                new Availability("mon:0900-1000,tue:1100-1200,wed:1200-1300,sat:1800-1900"),
                new ProgressRecord("5.5%")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new InjuryStatus("Shoulder Injury"),
                new TrainingGoal("50m sprint"),
                new Availability("mon:0900-1000,tue:1500-1600,wed:1800-2000,sun:1900-2000"),
                new ProgressRecord("1%")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new InjuryStatus("None"),
                new TrainingGoal("2 min 2.4k"),
                new Availability("mon:0800-1000,tue:1500-1700,wed:1800-1900"),
                new ProgressRecord("100%")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                new InjuryStatus("Resting"),
                new TrainingGoal("fly"),
                new Availability("mon:0900-1000"),
                new ProgressRecord("100%"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

}
