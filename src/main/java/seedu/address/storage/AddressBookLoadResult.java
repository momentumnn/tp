package seedu.address.storage;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents the metadata returned when the address book storage is read.
 *
 * <p>It carries the optionally loaded {@link seedu.address.model.ReadOnlyAddressBook},
 * a flag for whether the data file existed, whether any invalid entries were skipped,
 * the path where those entries were saved (when available), and any failure message
 * generated while trying to persist them.</p>
 */
public class AddressBookLoadResult {

    private final Optional<ReadOnlyAddressBook> addressBook;
    private final boolean dataFileExists;
    private final boolean hasInvalidEntries;
    private final Optional<Path> invalidEntriesFilePath;
    private final Optional<String> invalidEntriesSaveFailureMessage;

    /**
     * Creates a new result describing what happened during storage read.
     */
    public AddressBookLoadResult(Optional<ReadOnlyAddressBook> addressBook, boolean dataFileExists,
                                 boolean hasInvalidEntries, Optional<Path> invalidEntriesFilePath,
                                 Optional<String> invalidEntriesSaveFailureMessage) {
        this.addressBook = addressBook;
        this.dataFileExists = dataFileExists;
        this.hasInvalidEntries = hasInvalidEntries;
        this.invalidEntriesFilePath = invalidEntriesFilePath;
        this.invalidEntriesSaveFailureMessage = invalidEntriesSaveFailureMessage;
    }

    /** Returns the loaded address book when present. */
    public Optional<ReadOnlyAddressBook> getAddressBook() {
        return addressBook;
    }

    /** Returns true when the backing file existed. */
    public boolean hasDataFile() {
        return dataFileExists;
    }

    /** Returns true when the JSON contained invalid entries. */
    public boolean hasInvalidEntries() {
        return hasInvalidEntries;
    }

    /** Returns where skipped entries were saved, if any. */
    public Optional<Path> getInvalidEntriesFilePath() {
        return invalidEntriesFilePath;
    }

    /** Returns the reason saving invalid entries failed, if provided. */
    public Optional<String> getInvalidEntriesSaveFailureMessage() {
        return invalidEntriesSaveFailureMessage;
    }

    /** Returns the canonical result for "no file present" reads. */
    public static AddressBookLoadResult missingFile() {
        return new AddressBookLoadResult(Optional.empty(), false, false, Optional.empty(), Optional.empty());
    }
}
