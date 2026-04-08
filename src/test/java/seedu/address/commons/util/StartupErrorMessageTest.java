package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.StartupErrorMessage.FILE_LOAD_FAILURE_MESSAGE_FORMAT;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class StartupErrorMessageTest {

    private static final Path DATA_FILE_PATH = Paths.get("data", "addressbook.json");
    private static final Path INVALID_FILE_PATH = Paths.get("data", "addressbook-invalid.txt");

    @Test
    void buildDataLoadFailureMessage_validPath_returnsFormattedWarning() {
        String actual = StartupErrorMessage.buildDataLoadFailureMessage(DATA_FILE_PATH);
        String expected = String.format(FILE_LOAD_FAILURE_MESSAGE_FORMAT, DATA_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    void buildInvalidEntriesWarning_invalidEntriesSaved_returnsSavedFileMessage() {
        // EP: invalid entries found and successfully saved to a separate file
        String actual = StartupErrorMessage.buildInvalidEntriesWarning(
                DATA_FILE_PATH, Optional.of(INVALID_FILE_PATH), false);

        String expected = String.format(
                "Invalid entries were found in %s.\nWrong entries saved to %s",
                DATA_FILE_PATH, INVALID_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    void buildInvalidEntriesWarning_saveFails_returnsSaveFailureMessage() {
        // EP: invalid entries found but saving them failed
        String actual = StartupErrorMessage.buildInvalidEntriesWarning(
                DATA_FILE_PATH, Optional.empty(), true);

        String expected = String.format(
                "Invalid entries were found in %s but saving them failed.\n"
                        + "Check the addressbook.log file in the same folder as the app for more information.",
                DATA_FILE_PATH, "Permission denied");

        assertEquals(expected, actual);
    }

    @Test
    void buildInvalidEntriesWarning_noSavedFileAndNoFailure_returnsDefaultMessage() {
        // EP: invalid entries found, but neither saved-file path nor save-failure message is available
        String actual = StartupErrorMessage.buildInvalidEntriesWarning(
                DATA_FILE_PATH, Optional.empty(), false);

        String expected = String.format(
                "Invalid entries were found in %s",
                DATA_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    void buildInvalidEntriesWarning_bothPathAndFailurePresent_prioritizesFailureMessage() {
        // Boundary / precedence case:
        // both optionals are present, verify that save failure branch is give priority
        String actual = StartupErrorMessage.buildInvalidEntriesWarning(
                DATA_FILE_PATH, Optional.of(INVALID_FILE_PATH), true);

        String expected = String.format(
                "Invalid entries were found in %s but saving them failed.\n"
                        + "Check the addressbook.log file in the same folder as the app for more information.",
                DATA_FILE_PATH, "Permission denied");

        assertEquals(expected, actual);
    }
}
