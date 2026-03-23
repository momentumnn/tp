package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import static seedu.address.commons.util.StartupErrorMessage.WARNING_MESSAGE_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Skill;

class StartupErrorMessageTest {

    private static final Path DATA_FILE_PATH = Paths.get("data", "addressbook.json");

    @Test
    void build_withSkillConstraintMessage_includesReason() {
        DataLoadingException exception = new DataLoadingException(
                new IllegalValueException(Skill.MESSAGE_CONSTRAINTS));
        String actual = StartupErrorMessage.build(DATA_FILE_PATH, exception);
        String expected = String.format(WARNING_MESSAGE_FORMAT, DATA_FILE_PATH.toString(),
                Skill.MESSAGE_CONSTRAINTS);
        assertEquals(expected, actual);
    }

    @Test
    void build_withBlankCauseMessage_usesFallbackReason() {
        DataLoadingException exception = new DataLoadingException(new IllegalValueException("  "));
        String actual = StartupErrorMessage.build(DATA_FILE_PATH, exception);
        String expected = String.format(WARNING_MESSAGE_FORMAT, DATA_FILE_PATH.toString(),
                StartupErrorMessage.FALLBACK_REASON);
        assertEquals(expected, actual);
    }

    @Test
    void build_withNullCause_usesFallbackReason() {
        DataLoadingException exception = new DataLoadingException(null);
        String actual = StartupErrorMessage.build(DATA_FILE_PATH, exception);
        String expected = String.format(WARNING_MESSAGE_FORMAT, DATA_FILE_PATH.toString(),
                StartupErrorMessage.FALLBACK_REASON);
        assertEquals(expected, actual);
    }

    @Test
    void build_withNullException_usesFallbackReason() {
        DataLoadingException exception = null;
        String actual = StartupErrorMessage.build(DATA_FILE_PATH, exception);
        String expected = String.format(WARNING_MESSAGE_FORMAT, DATA_FILE_PATH.toString(),
                StartupErrorMessage.FALLBACK_REASON);
        assertEquals(expected, actual);
    }
}
