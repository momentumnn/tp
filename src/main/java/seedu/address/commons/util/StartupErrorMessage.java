package seedu.address.commons.util;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Utility class for building user-friendly startup warnings related to data loading.
 */
public final class StartupErrorMessage {
    static final String FILE_LOAD_FAILURE_MESSAGE_FORMAT =
            "WARNING: Data file at %s could not be loaded. Starting with an empty AddressBook.\n"
                    + "Check the addressbook.log file in the same folder as the app for more information.";

    private static final String SAVED_INVALID_ENTRIES_MESSAGE =
            "Invalid entries were found in %s.\n"
                    + "Wrong entries saved to %s";
    private static final String UNSAVED_INVALID_ENTRIES_MESSAGE =
            "Invalid entries were found in %s but saving them failed.\n"
                    + "Check the addressbook.log file in the same folder as the app for more information.";
    private static final String DEFAULT_INVALID_ENTRIES_MESSAGE =
            "Invalid entries were found in %s";

    private StartupErrorMessage() {}

    /**
     * Returns the startup warning shown when the data file cannot be loaded.
     */
    public static String buildDataLoadFailureMessage(Path dataFilePath) {
        return String.format(FILE_LOAD_FAILURE_MESSAGE_FORMAT, dataFilePath);
    }

    /**
     * Returns the startup warning shown when some invalid entries were skipped.
     */
    public static String buildInvalidEntriesWarning(Path dataFilePath, Optional<Path> invalidFilePath,
                                                    Optional<String> saveFailureMessage) {
        if (saveFailureMessage.isPresent()) {
            return String.format(UNSAVED_INVALID_ENTRIES_MESSAGE, dataFilePath);
        }
        if (invalidFilePath.isPresent()) {
            return String.format(SAVED_INVALID_ENTRIES_MESSAGE, dataFilePath, invalidFilePath.get());
        }
        return String.format(DEFAULT_INVALID_ENTRIES_MESSAGE, dataFilePath);
    }
}
