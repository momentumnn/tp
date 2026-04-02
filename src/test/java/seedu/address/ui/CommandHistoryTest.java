package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private CommandHistory commandHistory;

    @BeforeEach
    public void setUp() {
        commandHistory = new CommandHistory();
    }

    @Test
    public void navigateUp_emptyHistory_returnsEmptyString() {
        assertEquals("", commandHistory.navigateUp());
    }

    @Test
    public void navigateDown_emptyHistory_returnsEmptyString() {
        assertEquals("", commandHistory.navigateDown());
    }

    @Test
    public void navigate_singleCommand_returnsCorrectSequence() {
        String cmd = "list";
        commandHistory.addCommand(cmd);

        // After adding, pointer is at the "end" (index 1)
        // Up once -> "list"
        assertEquals(cmd, commandHistory.navigateUp());
        // Up again -> stays at "list"
        assertEquals(cmd, commandHistory.navigateUp());
        // Down -> returns to blank line
        assertEquals("", commandHistory.navigateDown());
    }

    @Test
    public void navigate_multipleCommands_navigatesCorrectly() {
        String cmd1 = "help";
        String cmd2 = "add n/John";
        String cmd3 = "list";

        commandHistory.addCommand(cmd1);
        commandHistory.addCommand(cmd2);
        commandHistory.addCommand(cmd3);

        // Current state: [help, add n/John, list] | pointer at 3 (blank)

        // Navigate Up
        assertEquals(cmd3, commandHistory.navigateUp()); // list
        assertEquals(cmd2, commandHistory.navigateUp()); // add n/John
        assertEquals(cmd1, commandHistory.navigateUp()); // help
        assertEquals(cmd1, commandHistory.navigateUp()); // boundary check: stays at help

        // Navigate Down
        assertEquals(cmd2, commandHistory.navigateDown()); // add n/John
        assertEquals(cmd3, commandHistory.navigateDown()); // list
        assertEquals("", commandHistory.navigateDown()); // boundary check: blank line
        assertEquals("", commandHistory.navigateDown()); // stays at blank line
    }

    @Test
    public void addCommand_resetsPointer() {
        commandHistory.addCommand("first");
        commandHistory.addCommand("second");

        // Move pointer back to "first"
        commandHistory.navigateUp();
        commandHistory.navigateUp();

        // Add a new command
        commandHistory.addCommand("third");

        // The pointer should have been reset to the end.
        // Calling navigateUp() should now return the latest command "third".
        assertEquals("third", commandHistory.navigateUp());
    }
}
