package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);
    private static final DateTimeFormatter BACKUP_TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private static final String INVALID_ENTRY_FILE_SUFFIX = "-invalid-";
    private static final String INVALID_ENTRY_FILE_EXTENSION = "txt";

    private Path filePath;

    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public AddressBookLoadResult readAddressBookWithResult() throws DataLoadingException {
        return readAddressBookWithResult(filePath);
    }

    @Override
    public AddressBookLoadResult readAddressBookWithResult(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook =
                JsonUtil.readJsonFile(filePath, JsonSerializableAddressBook.class);
        if (jsonAddressBook.isEmpty()) {
            return AddressBookLoadResult.missingFile();
        }

        JsonSerializableAddressBook serializableAddressBook = jsonAddressBook.get();
        ReadOnlyAddressBook addressBook = serializableAddressBook.toModelType();
        List<InvalidPersonRecord> invalidRecords = serializableAddressBook.getInvalidPersonRecords();

        logInvalidRecords(filePath, invalidRecords);

        Optional<Path> invalidEntriesFilePath = Optional.empty();
        Optional<String> invalidEntriesSaveFailureMessage = Optional.empty();

        if (!invalidRecords.isEmpty()) {
            try {
                invalidEntriesFilePath = saveInvalidEntries(invalidRecords, filePath);
            } catch (IOException e) {
                logger.warning("Failed to save invalid entries: " + e);
                invalidEntriesSaveFailureMessage = Optional.ofNullable(e.toString());
            }
        }

        return new AddressBookLoadResult(
                Optional.of(addressBook),
                true,
                !invalidRecords.isEmpty(),
                invalidEntriesFilePath,
                invalidEntriesSaveFailureMessage);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

    /**
     * Saves invalid records to a side file and returns its path.
     */
    private Optional<Path> saveInvalidEntries(List<InvalidPersonRecord> invalidRecords, Path originalFilePath)
            throws IOException {
        if (invalidRecords.isEmpty()) {
            return Optional.empty();
        }

        Path invalidFilePath = buildInvalidEntriesPath(originalFilePath);
        FileUtil.createIfMissing(invalidFilePath);
        FileUtil.writeToFile(invalidFilePath, buildInvalidEntriesContent(invalidRecords));
        logger.info("Invalid entries saved to " + invalidFilePath);
        return Optional.of(invalidFilePath);
    }

    /**
     * Logs each invalid record skipped during loading.
     */
    private void logInvalidRecords(Path filePath, List<InvalidPersonRecord> invalidRecords) {
        invalidRecords.forEach(record ->
                logger.warning("Skipped invalid entry in " + filePath + ": " + record.getReason()));
    }

    /**
     * Returns the text content to be written to the invalid-entries file.
     */
    private String buildInvalidEntriesContent(List<InvalidPersonRecord> invalidRecords)
            throws JsonProcessingException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < invalidRecords.size(); i++) {
            if (i > 0) {
                builder.append(System.lineSeparator()).append(System.lineSeparator());
            }
            InvalidPersonRecord record = invalidRecords.get(i);
            builder.append("Reason: ").append(record.getReason()).append(System.lineSeparator());
            builder.append(JsonUtil.toJsonString(record.getAdaptedPerson()));
        }
        return builder.toString();
    }

    /**
     * Returns the path for the invalid-entries side file.
     */
    private Path buildInvalidEntriesPath(Path originalPath) {
        String invalidFileName = buildInvalidEntriesFileName(originalPath.getFileName().toString());
        return originalPath.resolveSibling(invalidFileName);
    }

    /**
     * Returns the invalid-entries file name derived from the original data file name.
     */
    private String buildInvalidEntriesFileName(String fileName) {
        String timestamp = LocalDateTime.now().format(BACKUP_TIMESTAMP_FORMAT);
        String baseName = fileName;
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0) {
            baseName = fileName.substring(0, dotIndex);
        }

        return baseName + INVALID_ENTRY_FILE_SUFFIX + timestamp + "." + INVALID_ENTRY_FILE_EXTENSION;
    }
}
