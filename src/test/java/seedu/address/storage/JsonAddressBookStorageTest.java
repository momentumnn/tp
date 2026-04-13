package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        // EP: read path is null
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        Path sourcePath = addToTestDataPathIfNotNull(filePath);
        if (sourcePath == null) {
            return new JsonAddressBookStorage(Paths.get(filePath)).readAddressBook(null);
        }

        JsonAddressBookStorage storage;
        Path fileToRead;
        if (Files.exists(sourcePath)) {
            Path tempFile = testFolder.resolve(sourcePath.getFileName());
            Files.copy(sourcePath, tempFile, StandardCopyOption.REPLACE_EXISTING);
            storage = new JsonAddressBookStorage(tempFile);
            fileToRead = tempFile;
        } else {
            storage = new JsonAddressBookStorage(sourcePath);
            fileToRead = sourcePath;
        }
        return storage.readAddressBook(fileToRead);
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    private Path copyToTemp(String fileName) throws IOException {
        Path source = addToTestDataPathIfNotNull(fileName);
        Path destination = testFolder.resolve(fileName);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        return destination;
    }

    private Path copyFromSerializableTestData(String fileName) throws IOException {
        Path source = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest", fileName);
        Path destination = testFolder.resolve(fileName);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        return destination;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        // EP: data file missing
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void readAddressBookWithResult_missingFile_returnsMissingResult() throws Exception {
        // EP: data file missing for readAddressBookWithResult
        Path missingFile = addToTestDataPathIfNotNull("NonExistentFile.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(missingFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(missingFile);

        assertFalse(result.hasDataFile());
        assertFalse(result.hasInvalidEntries());
        assertTrue(result.getAddressBook().isEmpty());
        assertFalse(result.getInvalidEntriesFilePath().isPresent());
        assertFalse(result.getInvalidEntriesSaveFailureMessage().isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        // EP: file exists but JSON format is malformed
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_skipsInvalidEntries() throws Exception {
        // EP: all persons invalid
        // Expected: no valid persons loaded, invalid entries reported and saved
        Path tempInvalidFile = copyToTemp("invalidPersonAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(tempInvalidFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(tempInvalidFile);
        ReadOnlyAddressBook readOnlyAddressBook = result.getAddressBook().orElseThrow();

        assertTrue(readOnlyAddressBook.getPersonList().isEmpty());
        assertTrue(result.hasInvalidEntries());
        assertTrue(result.getInvalidEntriesFilePath().isPresent());
        assertFalse(result.getInvalidEntriesSaveFailureMessage().isPresent());

        Path invalidPath = result.getInvalidEntriesFilePath().orElseThrow();
        String contents = Files.readString(invalidPath);
        assertTrue(contents.contains("Reason:"));
        assertTrue(contents.contains("\"name\""));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_generatesInvalidEntriesTxtFile() throws Exception {
        // EP: invalid entries exist and invalid-entry file save succeeds
        // Expected: generated invalid-entry file uses .txt suffix and contains "-invalid-"
        Path tempInvalidFile = copyToTemp("invalidPersonAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(tempInvalidFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(tempInvalidFile);
        Path invalidPath = result.getInvalidEntriesFilePath().orElseThrow();

        String invalidFileName = invalidPath.getFileName().toString();
        assertTrue(invalidFileName.contains("-invalid-"));
        assertTrue(invalidFileName.endsWith(".txt"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_skipsInvalidAndKeepsValid() throws Exception {
        // EP: mixture of valid and invalid persons
        // Expected: valid persons kept, invalid persons skipped
        Path tempFile = copyToTemp("invalidAndValidPersonAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(tempFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(tempFile);
        ReadOnlyAddressBook readOnlyAddressBook = result.getAddressBook().orElseThrow();

        assertEquals(1, readOnlyAddressBook.getPersonList().size());
        assertEquals("Valid Person", readOnlyAddressBook.getPersonList().get(0).getName().fullName);
        assertTrue(result.hasInvalidEntries());
        assertTrue(result.getInvalidEntriesFilePath().isPresent());
        assertFalse(result.getInvalidEntriesSaveFailureMessage().isPresent());
    }

    @Test
    public void readAddressBook_nullTimeslotPerson_skipsInvalidEntry() throws Exception {
        // EP: invalid person caused by null timeslot entry
        Path tempFile = copyToTemp("invalidNullTimeslotAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(tempFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(tempFile);
        ReadOnlyAddressBook readOnlyAddressBook = result.getAddressBook().orElseThrow();

        assertTrue(readOnlyAddressBook.getPersonList().isEmpty());
        assertTrue(result.hasInvalidEntries());
        assertTrue(result.getInvalidEntriesFilePath().isPresent());
        assertFalse(result.getInvalidEntriesSaveFailureMessage().isPresent());
    }

    @Test
    public void readAddressBook_duplicateDayTimeslotPerson_skipsInvalidEntry() throws Exception {
        // EP: invalid person caused by duplicate-day timeslot
        Path tempFile = copyToTemp("duplicateDayTimeslotAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(tempFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(tempFile);
        ReadOnlyAddressBook readOnlyAddressBook = result.getAddressBook().orElseThrow();

        assertTrue(readOnlyAddressBook.getPersonList().isEmpty());
        assertTrue(result.hasInvalidEntries());
        assertTrue(result.getInvalidEntriesFilePath().isPresent());
        assertFalse(result.getInvalidEntriesSaveFailureMessage().isPresent());
    }

    @Test
    public void readAddressBook_allValid_noInvalidFileGenerated() throws Exception {
        // EP: all persons valid
        // Expected: full address book loaded and no invalid-entry output generated
        Path tempFile = copyFromSerializableTestData("typicalPersonsAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(tempFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(tempFile);
        ReadOnlyAddressBook readOnlyAddressBook = result.getAddressBook().orElseThrow();

        assertFalse(result.hasInvalidEntries());
        assertFalse(result.getInvalidEntriesFilePath().isPresent());
        assertFalse(result.getInvalidEntriesSaveFailureMessage().isPresent());
        assertEquals(getTypicalAddressBook(), new AddressBook(readOnlyAddressBook));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // EP: save into a new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).orElseThrow();
        assertEquals(original, new AddressBook(readBack));

        // EP: overwrite an existing file and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).orElseThrow();
        assertEquals(original, new AddressBook(readBack));

        // EP: save and read without specifying file path explicitly
        original.addPerson(IDA);
        jsonAddressBookStorage.saveAddressBook(original);
        readBack = jsonAddressBookStorage.readAddressBook().orElseThrow();
        assertEquals(original, new AddressBook(readBack));
    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        // EP: address book to save is null
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        // EP: file path to save is null
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }
}
