package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void saveAddressBook_withFilePath_success() throws Exception {
        Path explicitPath = getTempFilePath("explicitAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original, explicitPath);
        ReadOnlyAddressBook saved = new JsonAddressBookStorage(explicitPath).readAddressBook(explicitPath).get();
        assertEquals(original, new AddressBook(saved));
    }

    @Test
    public void readAddressBook_withFilePath_success() throws Exception {
        Path explicitPath = getTempFilePath("explicitReadAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        new JsonAddressBookStorage(explicitPath).saveAddressBook(original, explicitPath);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook(explicitPath).get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void readAddressBookWithResult_missingFile_returnsMissingResult() throws Exception {
        Path missingPath = getTempFilePath("missingAddressBook.json");
        AddressBookLoadResult result = storageManager.readAddressBookWithResult(missingPath);
        assertFalse(result.hasDataFile());
        assertFalse(result.hasInvalidEntries());
        assertTrue(result.getAddressBook().isEmpty());
    }

    @Test
    public void readAddressBookWithResult_defaultPath_success() throws Exception {
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        AddressBookLoadResult result = storageManager.readAddressBookWithResult();
        assertTrue(result.hasDataFile());
        assertFalse(result.hasInvalidEntries());
        assertEquals(original, new AddressBook(result.getAddressBook().get()));
    }

    @Test
    public void readAddressBookWithResult_withFilePath_success() throws Exception {
        Path explicitPath = getTempFilePath("explicitResultAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        new JsonAddressBookStorage(explicitPath).saveAddressBook(original, explicitPath);
        AddressBookLoadResult result = storageManager.readAddressBookWithResult(explicitPath);
        assertTrue(result.hasDataFile());
        assertFalse(result.hasInvalidEntries());
        assertEquals(original, new AddressBook(result.getAddressBook().get()));
    }

}
