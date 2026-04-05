---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# PTcoach User Guide

PTcoach is a **desktop app for managing client contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, PTcoach can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103-F11-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your PTcoach.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar PTcoach.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 t/Run 100km ts/mon:1,3,5;tue:7 i/Healthy s/Beginner pr/50` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

### Command History Navigation

PTCoach supports command history navigation similar to Unix command-line systems.

* Press the **Up** arrow key to view previously entered commands.
* Press the **Down** arrow key to move towards more recent commands.
* This allows users to quickly reuse or edit past commands without retyping them.

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [s/SKILL]` can be used as `n/John Doe s/intermediate` or as `n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS ts/TIMESLOT t/TRAINING_GOAL [pr/PROGRESS_RECORD] [i/INJURY_STATUS] [s/SKILL]`

**Name (`n/`):**
* Represents the full name of the client (e.g. `John Doe`)
* Accepts alphabetical characters and spaces only — no numbers or symbols
* Cannot be blank
* This field is mandatory

**Phone Number (`p/`):**
* Represents the contact number of the client (e.g. `98765432`)
* Accepts numerals only — no spaces, dashes, or other characters
* Must be between 3 and 15 digits long
* This field is mandatory

**Email (`e/`):**
* Represents the email address of the client (e.g. `johnd@example.com`)
* Must follow the format `local-part@domain` (e.g. `john@example.com`)
* Both the local-part and domain cannot be blank
* This field is mandatory

**Address (`a/`):**
* Represents the home or mailing address of the client (e.g. `John street, block 123, #01-01`)
* Accepts any non-blank characters
* Cannot be blank
* This field is mandatory

**Timeslot (`ts/`):**
* Represents the weekly training schedule of the client (e.g. `mon:1,3,5;tue:7`)
* Must follow the format: 'day:slot[,slot...];day:slot'
* Days must be a 3-letter abbreviations (`mon`, `tue`, `wed`, `thu`, `fri`, `sat`, `sun`)
* Slots are integers from **1 to 12**, each representing a fixed 1-hour time period.
  * eg. Slot 1 -> 0800 - 0900 and Slot 12 -> 1900 - 2000
* Multiple slots for the same day are separated by commas
* Multiple days are separated by semicolons
* No duplicate slots allowed for the same day
* Cannot be blank
* This field is mandatory - every client must have a timeslot.

**Training Goal (`t/`):**
* Represents the fitness or performance goal of the client (e.g. `run 50km`, `lift 100kg`)
* Accepts any alphanumeric characters and spaces
* Must not contain substrings that resemble command prefixes (e.g. `a/`, `p/`, `i/`, `t/`, `ts/`, `s/`, `pr/`)
* Cannot be blank
* The max length for the training goal is 200 characters
* This field is mandatory — every client must have a training goal specified

**Progress Record (`pr/`):**
* Represents the client’s training progress as a percentage (e.g. `50`, `100`)
* Accepts integers from `0 to 100`
* Must be a whole number (no symbols such as `%` or fractions)
* Cannot be blank if provided
* This field is optional

**Injury Status (`i/`):**
* Represents any injury or physical limitation of the client (e.g. `L4/L5 disc herniation`, `ACL tore`)
* Accepts any alphanumeric characters and spaces
* Must not contain substrings that resemble command prefixes (e.g. `a/`, `p/`, `i/`, `t/`, `ts/`, `s/`, `pr/`)
* Must not exceed 300 characters
* Cannot be blank if provided
* This field is optional

**Skill Level (`s/`):**
* Represents the client’s current fitness or skill level
* Must be one of the following values: `beginner`, `intermediate`, `expert`
* Input is case-insensitive (e.g. `Beginner`, `BEGINNER` are accepted)
* Will be stored in capitalized form (e.g. `Beginner`)
* Cannot be blank if provided
* This field is optional

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 t/Run 50km ts/mon:1,2 i/Shoulder dislocation`
* `add n/Betsy Crowe p/1234567 e/betsycrowe@example.com a/Newgate Prison t/Lift 100kg ts/mon:1,3;sat:2,4`

### Listing all persons : `list`

Shows a list of all persons in the address book, with an optional skill filter.

Format: `list [s/SKILL]`

**Skill Filter (`s/`):**
* Filters the list to show only clients with a matching skill level (e.g. `beginner`, `intermediate`, `expert`)
* Accepts any non-blank alphanumeric value — not restricted to predefined skill levels
* Multiple skill filters can be provided to match clients with any of the specified skill levels
* If no skill filter is provided, all persons are listed
* Note: Typos in the skill filter (e.g. `list s/begniner`) will not result in an error, but will return an empty list if no clients match the misspelled value

Examples:
* `list` — lists all persons
* `list s/beginner` — lists all persons with beginner skill level
* `list s/beginner s/expert` — lists all persons with beginner or expert skill level

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [ts/TIMESLOT] [t/TRAINING GOAL] [pr/PROGRESS_RECORD] [i/INJURY_STATUS] [s/SKILL]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

**Name (`n/`):**
* Represents the full name of the client (e.g. `John Doe`)
* Accepts alphabetical characters and spaces only — no numbers or symbols
* Cannot be blank

**Phone Number (`p/`):**
* Represents the contact number of the client (e.g. `98765432`)
* Accepts numerals only — no spaces, dashes, or other characters
* Must be between 3 and 15 digits long

**Email (`e/`):**
* Represents the email address of the client (e.g. `johnd@example.com`)
* Must follow the format `local-part@domain` (e.g. `john@example.com`)
* Both the local-part and domain cannot be blank

**Address (`a/`):**
* Represents the home or mailing address of the client (e.g. `John street, block 123, #01-01`)
* Accepts any non-blank characters
* Cannot be blank

**Timeslot (`ts/`):**
* Represents the weekly training schedule of the client (e.g. `mon:1,3,5;tue:7`)
* Must follow the format: 'day:slot[,slot...];day:slot'
* Days must be a 3-letter abbreviations (`mon`, `tue`, `wed`, `thu`, `fri`, `sat`, `sun`)
* Slots are integers from **1 to 12**, each representing a fixed 1-hour time period.
    * eg. Slot 1 -> 0800 - 0900 and Slot 12 -> 1900 - 2000
* Multiple slots for the same day are separated by commas
* Multiple days are separated by semicolons
* No duplicate slots allowed for the same day
* Cannot be blank

**Training Goal (`t/`):**
* Represents the fitness or performance goal of the client (e.g. `run 50km`, `lift 100kg`)
* Accepts any alphanumeric characters and spaces
* Must not contain substrings that resemble command prefixes (e.g. `a/`, `p/`, `i/`, `t/`, `ts/`, `s/`, `pr/`)
* Cannot be blank
* The max length for the training goal is 200 characters

**Progress Record (`pr/`):**
* Represents the client’s training progress as a percentage (e.g. `50`, `100`)
* Accepts integers from `0 to 100`
* Must be a whole number (no symbols such as `%` or fractions)
* Cannot be blank if provided

**Injury Status (`i/`):**
* Represents any injury or physical limitation of the client (e.g. `L4/L5 disc herniation`, `ACL tore`)
* Accepts any alphanumeric characters and spaces
* Must not contain substrings that resemble command prefixes (e.g. `a/`, `p/`, `i/`, `t/`, `ts/`, `s/`, `pr/`)
* Must not exceed 300 characters
* Cannot be blank if provided

**Skill Level (`s/`):**
* Represents the client’s current fitness or skill level (e.g. `Beginner`)
* Must be one of the following values: `beginner`, `intermediate`, `expert`
* Input is case-insensitive (e.g. `Beginner`, `BEGINNER` are accepted)
* Will be stored in capitalized form (e.g. `Beginner`)
* Cannot be blank if provided

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

PTcoach data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

PTcoach data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, PTcoach will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the PTcoach to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous PTcoach home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    |`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS t/TRAINING_GOAL ts/TIMESLOT [i/INJURY_STATUS] [s/SKILL] [pr/PROGRESS_RECORD]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 ts/sat:2,3 t/Run 100km`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [ts/TIMESLOT] [t/TRAINING GOAL] [pr/PROGRESS_RECORD] [i/INJURY_STATUS] [s/SKILL]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list [s/SKILL]`<br> e.g., `list s/intermediate`
**Help**   | `help`
