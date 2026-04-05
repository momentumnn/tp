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
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
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
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void readAddressBookWithResult_missingFile_returnsMissingResult() throws Exception {
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
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_skipsInvalidEntries() throws Exception {
        Path tempInvalidFile = copyToTemp("invalidPersonAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(tempInvalidFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(tempInvalidFile);
        ReadOnlyAddressBook readOnlyAddressBook = result.getAddressBook().get();
        assertTrue(readOnlyAddressBook.getPersonList().isEmpty());
        assertTrue(result.hasInvalidEntries());
        Path invalidPath = result.getInvalidEntriesFilePath().orElseThrow();
        String contents = Files.readString(invalidPath);
        assertTrue(contents.contains("Reason:"));
        assertTrue(contents.contains("\"name\""));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_skipsInvalidAndKeepsValid() throws Exception {
        Path tempFile = copyToTemp("invalidAndValidPersonAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(tempFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(tempFile);
        ReadOnlyAddressBook readOnlyAddressBook = result.getAddressBook().get();
        assertEquals(1, readOnlyAddressBook.getPersonList().size());
        assertEquals("Valid Person", readOnlyAddressBook.getPersonList().get(0).getName().fullName);
        assertTrue(result.hasInvalidEntries());
    }

    @Test
    public void readAddressBook_allValid_noInvalidFileGenerated() throws Exception {
        Path tempFile = copyFromSerializableTestData("typicalPersonsAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(tempFile);

        AddressBookLoadResult result = storage.readAddressBookWithResult(tempFile);
        ReadOnlyAddressBook readOnlyAddressBook = result.getAddressBook().get();
        assertFalse(result.hasInvalidEntries());
        assertEquals(getTypicalAddressBook(), new AddressBook(readOnlyAddressBook));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
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
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }

}
