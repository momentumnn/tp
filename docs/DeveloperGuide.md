---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# PTcoach Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Filter by Skill Feature

#### Implementation

The filter by skill mechanism is facilitated by an updated `ListCommand` and `ListCommandParser`.
The `list` command now accepts one or more optional `s/` prefixes, each specifying a skill level
to filter by. The following skill levels are supported: `Beginner`, `Intermediate`, and `Expert`
(case-insensitive).

Given below is an example usage scenario and how the filter by skill mechanism behaves at each step.

Step 1. The user launches the application. For the purpose of this example, assume the address
book contains the following 3 persons:

* `Alex` with skill level `Beginner`
* `Betty` with skill level `Intermediate`
* `Charlie` with skill level `Expert`

<box type="info" seamless>

**Note:** The diagrams below only show fields relevant to the filter by skill feature. For the
full list of attributes and relationships in each class, refer to the information above.

</box>

<puml src="diagrams/FilterSkillState0.puml" alt="FilterSkillState0" />

Step 2. The user executes `list s/expert`. `ListCommandParser` parses the input and returns a `ListCommand` containing `Expert` as the skill filter. `ListCommand#execute()` calls `Model#updateFilteredPersonList()` with a predicate that matches only persons with the `Expert` skill level. Only `Charlie` is shown, and the result message reflects 1 entry found.

<puml src="diagrams/FilterSkillState1.puml" alt="FilterSkillState1" />

Step 3. The user executes `list s/beginner s/expert`. `ListCommandParser` collects both values
and returns a `ListCommand` containing both skill levels. The predicate matches persons whose
skill level is either `Beginner` or `Expert`. `Alex` and `Charlie` are shown, and the result
message reflects 2 entries found.

<puml src="diagrams/FilterSkillState2.puml" alt="FilterSkillState2" />

<box type="info" seamless>

**Note:** Skill level matching is case-insensitive. `s/expert`, `s/EXPERT`, and `s/Expert` all
produce the same result.

</box>

Step 4. The user executes `list` with no `s/` prefix. `ListCommandParser` returns an unfiltered
`ListCommand`. `ListCommand#execute()` calls `Model#updateFilteredPersonList()` with
`PREDICATE_SHOW_ALL_PERSONS`, restoring the full list. All 3 persons are shown.

<puml src="diagrams/FilterSkillState3.puml" alt="FilterSkillState3" />

<box type="info" seamless>

**Note:** The `list` command does not modify the address book state and will not call
`Model#commitAddressBook()`. The `addressBookStateList` remains unchanged after any `list` command.

</box>

Step 5. The user executes `list s/advanced`. Since `advanced` is not a valid skill level,
`ListCommandParser` throws a `ParseException` and the filtered list is not updated.

The following sequence diagram shows how a `list s/expert` operation goes through the `Logic`
component:

<puml src="diagrams/FilterSkillSequenceDiagram-Logic.puml" alt="FilterSkillSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `ListCommandParser` should end at the destroy marker (X) but due to a
limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how the operation goes through the `Model` component is shown below:

<puml src="diagrams/FilterSkillSequenceDiagram-Model.puml" alt="FilterSkillSequenceDiagram-Model" />

The following activity diagram summarizes what happens when a user executes a `list` command:

<puml src="diagrams/FilterSkillActivityDiagram.puml" width="700"/>

#### Design Considerations

**Aspect: How skill filtering is applied:**

* **Alternative 1 (current choice):** Filter is applied via an inline predicate passed to `Model#updateFilteredPersonList()`.
    * Pros: Consistent with the existing filtering pattern used elsewhere in the codebase. No new predicate class required.
    * Cons: The predicate logic lives inside `ListCommand#execute()` rather than in a dedicated, testable class.

* **Alternative 2:** Extract the predicate into a dedicated `SkillContainsKeywordsPredicate` class.
    * Pros: Improves testability and separates concerns more cleanly.
    * Cons: Adds an extra class for relatively simple logic.

**Aspect: How multiple skill filters are combined:**

* **Alternative 1 (current choice):** Multiple `s/` prefixes are combined with OR logic — persons matching any of the specified skill levels are shown.
    * Pros: Intuitive for listing a broad selection (e.g. beginners and experts together).
    * Cons: AND logic (intersection) is not supported.

* **Alternative 2:** Multiple `s/` prefixes are combined with AND logic.
    * Pros: Useful if a person can hold multiple skill levels simultaneously.
    * Cons: Since each person has exactly one skill level, AND logic across different values would always return an empty list, making it unintuitive and unhelpful.

_{more aspects and alternatives to be added}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Personal Trainers who manage a large client base and track client progress.
* Has a need to quickly access their contact details.
* Prefers a desktop app with an easy-to-use interface.
* Manages clients and their injuries efficiently.

**Value proposition**: Provides a centralized system for personal trainers to efficiently manage client contact details, track injury histories, and monitor training goals and skill progress, optimized for users who prefer a fast, keyboard-driven interface.
### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​    | I want to …​                                   | So that I can…​                                              |
|------|------------|------------------------------------------------|---------------------------------------------------------------|
| `* * *` | trainer    | add a client with their personal particulars   | create a basic contact record for a new client             |
| `* * *` | trainer    | find a client by searching for their name      | quickly access a specific person's profile                    |
| `* * *` | trainer    | delete a specific client record                | remove clients who are no longer training with me             |
| `* * *` | trainer    | list all clients currently in the system       | see an overview of my entire client base                      |
| `* * *` | trainer    | view a client's injury history                 | plan a safe workout before the session starts                 |
| `* *` | trainer    | record a trainee's weekly training timeslot    | efficiently plan my training schedule in advance              |
| `* * *` | trainer    | save my client data                            | never lose my client data                                     |
| `* * *` | trainer    | update a client's contact details              | maintain accurate contact information                         |
| `* * *` | new user   | launch the app via the command line            | start managing my data quickly                                |
| `* * *` | trainer    | record a new injury for a client               | keep their health profile up to date                          |
| `* * *` | trainer    | set an initial training level (e.g., Beginner) | know where to start a new client's training workout      |
| `* * *` | trainer    | filter the list by training level              | plan group sessions for similar abilities                     |
| `*`  | impatient user | get search results in under 200ms              | not feel held up while on the gym floor                       |
| `* * *` | trainer    | undo the last command executed                 | quickly fix accidental deletions or edits                     |
| `*`  | trainer    | list all clients who have no recorded injuries | identify clients who can handle high-intensity workouts  |
| `*`  | expert user | use short aliases (e.g., `a` for add)          | enter data faster during back-to-back sessions                |
| `*`  | trainer    | edit an existing progress note                 | correct typos or add more detail later                        |
| `*`  | trainer    | clear the screen with a command                | keep my terminal interface tidy and focused                   |
| `*`  | expert user | perform multi-parameter searches               | find beginners with back injuries more specifically           |
| `*`  | expert user | export a summary report of all clients         | review my monthly coaching impact offline                     |

### Use cases

(For all use cases below, the **Actor** is the `Trainer` and the **System** is the `PTcoach`, unless specified otherwise)

**Use case: UC1 - Add a client**

**MSS**

1. Trainer requests to add a client.
2. PTcoach shows a success message.

    Use case ends.

**Extensions**
* 1a. The given details are invalid.
  * 1a1. PTcoach shows an error message.
    
    Use case ends.
  
* 1b. The given client exists.
  * 1b1. PTcoach shows an error message.

    Use case ends.

* 1c. The given command has an incorrect format.
  * 1c1. PTcoach shows an error message.
  
    Use case ends.

**Use case: UC2 - Find a specific client**

**MSS**

1. User requests to find a specific clients.
2. PTcoach shows list of all clients that match the person(s).

   Use case ends.

**Extensions**

* 1a. Trainer searches for an invalid name.
  * 1a1. PTcoach shows an error message.
    
    Use case ends.

* 1b. The given command has an incorrect format.
  * 1b1. PTcoach shows an error message.
  
    Use case ends.
        
* 2a. The list is empty.
  
  Use case ends.

**Use case: UC3 - Edit a client**

**MSS**

1. Trainer requests to edit a specific client.
2. PTcoach shows a success message.

    Use case ends.

**Extensions**

* 1a. Invalid index
  * 1a1. PTcoach shows an error message.
  
    Use case ends.
  
* 1b. Has missing parameters
  * 1b1. PTcoach shows an error message.
        
    Use case ends.

* 1c. No changes found
  * 1c1. PTcoach shows a success message.

    Use case ends.

* 1d. Incorrect format.
  * 1d1. PTcoach shows an error message.

    Use case ends.

**Use case: UC4 - Delete a client**

**MSS**

1. User requests to delete a specific client by index.
2. PTcoach deletes the client.
3. PTcoach shows a success message confirming the deletion.

   Use case ends

**Extensions**

* 1a. The list is empty.
  * 1a1. PTcoach shows an error message.

    Use case ends.

* 1b. The client does not exist.
  * 1b1. PTcoach shows an error message.

    Use case ends.

* 1c. The given command is in an incorrect format.
  * 1c1. PTcoach shows an error message.
   
    Use case ends.

**Use case: UC5 - Launch the app via command line**

**MSS**

1. User requests to launch the app via command line.
2. PTcoach launches.

   Use case ends.

**Extensions**

* 1a. The given command is in an incorrect format.
  
  Use case ends.

**Use case: UC6 - List all clients**

**MSS**

1. Trainer requests to view clients (optionally filtered by skill).
2. PTcoach shows a list of all clients.
3. PTcoach shows a list of clients matching the request.

   Use case ends.

**Extensions**

* 1a. The given command is in an incorrect format.
  * 1a1. PTcoach shows an error message.
   
    Use case ends.

* 2a. The list is empty.
  * 2a1. PTcoach shows a message indicating that the list is empty.
   
    Use case ends.

* 3a. No clients match the filter. 
  * 3a1. PTcoach shows an empty list. 
  
    Use case ends.
  
* 3b. Missing filter parameter
  * 3b1. PTcoach shows a message indicating that the parameter is empty.
  
    Use case ends.

**Use case: UC7 - Read client details**

**MSS**

1. Trainer requests to view a client’s details.
2. PTcoach displays the requested client data.

   Use case ends

**Extensions**

* 1a. The given details are invalid.
  * 1a1. PTcoach shows an error message.
   
    Use case ends.

* 1b. Client does not exist
  * 1b1. PTcoach shows an error message.

    Use case ends
  
* 1c. Trainer enters incorrect command format.
  * 1c1. PTcoach shows an error message.
     
    Use case ends.

**Use case: UC8 - Navigate command history**

**MSS**

1. Trainer presses the Up or Down arrow key.
2. PTcoach displays the corresponding command from the command history.

    Use case ends.

**Extensions**

* 1a. There are no previously entered commands.  
  * 1a1. PTcoach does not display any command.
  
  Use case ends.  

* 1b. Trainer presses Up when already at the oldest command.
  * 1b1. PTcoach keeps displaying the oldest command. 
  
  Use case ends. 

* 1c. Trainer presses Down when already at the most recent command.
  * 1c1. PTcoach displays an empty input field.
  
  Use case ends.


*{More to be added}*

### Non-Functional Requirements

1.  Should work on any mainstream OS (Windows, macOS and Linux) as long as it has Java 17 installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should be usable by a novice who has never created a client address book before.
5.  Should be able to return search results in under 3s.
6.  Should store data locally only.
7.  Should only be closed using the “exit” command or window close button or by killing the process.
8.  Command input should not exceed 500 words.
9.  Should only be opened through the command line.

*{More to be added}*

### Glossary

* **Personal Particulars**: The set of information stored for each client, including:
  - Name  
  - Contact Number  
  - Address   
  - Timeslot
  - Training Goals  
  - Skill Level*  
  - Progress Record*  
  - Injury Status*  

  \* Optional particulars

* **Field**: A property of a person stored in the system

* **Client**: The person being added into the address book

* **Trainer**: The user of the program

* **Duplicate clients**: 2 Clients with the same phone number

* **Timeslot**: A field in a client which represents the timeslot allocated for training by the trainer

*{More to be added}*

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

3. Exiting the application

    1. Test Case: `exit`<br>
       Expected: The application closes.
   
   2. Click on the exit icon of the application
      Expected: The application closes.

### Adding a person

1. Adding a person with all compulsory fields
   1. Prerequisites: The application is running normally.
   
   2. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 t/Run 50km ts/mon:1,2`<br>
   Expected: A new person is added to the list. A success message is shown.

2. Adding a person with all fields

   1. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 t/Run 50km ts/mon:1,2 i/Shoulder dislocation s/beginner pr/50`<br>
      Expected: A new person is added to the list with all provided details. A success message is shown.

3. Adding a duplicate person

   1. Prerequisites: A person with phone number `98765432` already exists.

   2. Test case: `add n/Jane Doe p/98765432 e/janed@example.com a/Jane street t/Lift 100kg ts/tue:3`<br>
      Expected: No person is added. An error message is shown.

4. Adding a person with invalid fields

   1. Test case: `add n/John Doe p/abc123 e/johnd@example.com a/John street t/Run 50km ts/mon:1,2`<br>
      Expected: No person is added. An error message is shown because the phone number is invalid

   2. Test case: `add n/John Doe p/98765432 e/invalidEmail a/John street t/Run 50km ts/mon:1,2`<br>
      Expected: No person is added. An error message is shown because the email is invalid
   
   3. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/John street t/Run 50km ts/mon:13`<br>
      Expected: No person is added. An error message is shown because the timeslot is invalid

5. Adding a person with missing compulsory fields

   1. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/John street t/Run 50km`<br>
          Expected: No person is added. An error message is shown because the timeslot is missing.

   2. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/John street ts/mon:1,2`<br>
      Expected: No person is added. An error message is shown because the training goal is missing.

### Listing persons

1. Listing all persons

   1. Test case: `list`<br>
      Expected: All persons in the address book are shown.

2. Listing persons by skill filter 

   1. Prerequisites: There are persons with different skill levels in the list.

   2. Test case: `list s/beginner`<br>
      Expected: Only persons with skill level `Beginner` are shown.

   3. Test case: `list s/expert`<br>
      Expected: Only persons with skill level `Expert` are shown.
   
   4. Test case: `list s/beginner s/intermediate`
      Expected: Only persons with skill level `Beginner` and `Intermediate` are shown.

3. Listing persons with no matching filter

   1. Test case: `list s/beginner` when there are no beginner clients<br>
      Expected: An empty list is shown.

4. Listing persons with invalid filter

   1. Test case: `list s/advanced`
      Expected: The command is accepted but no client has the skill level `Advanced`. Hence, an empty list is shown.
   
   2. Test case: `list s/beginer`
      Expected: The command is accepted but no client has the skill level `beginer`. Hence, an empty list is shown. (note the beginer here has a typo error)

5. Listing persons with missing filter parameter

   1. Test case: `list s/`
      Expected: An error message is shown because the skill filter is blank.

### Editing a person

1. Editing a person with one field

   1. Prerequisites: List all persons using the `list` command. At least one person exists.

   2. Test case: `edit 1 p/91234567`<br>
      Expected: The phone number of the 1st person is updated. A success message is shown.

2. Editing a person with multiple fields

   1. Test case: `edit 1 e/johndoe@example.com t/Lift 100kg ts/fri:2,3 pr/70 s/intermediate`<br>
      Expected: The specified fields of the 1st person are updated. A success message is shown.

3. Editing a person with invalid index
   
   1. Prerequisites: List all persons using the `list` command. There is at least one person in the list and fewer than 999 persons in the list.
      Note: The index refers to the position shown in the displayed list and starts from 1.

   2. Test case: `edit 0 p/91234567`<br>
      Expected: No person is edited. An error message is shown because index starts with 1 not 0. 
   
   3. Test case: `edit 999 p/91234567`<br>
      Expected: No person is edited. An error message is shown because index is out of range.

4. Editing a person with invalid values

   1. Test case: `edit 1 p/abc123`<br>
      Expected: No person is edited. An error message is shown because phone number is invalid.

   2. Test case: `edit 1 ts/mon:13`<br>
      Expected: No person is edited. An error message is shown because Timeslot is invalid.

   3. Test case: `edit 1 s/advanced`<br>
      Expected: No person is edited. An error message is shown because skill is invalid.

5. Editing a person without providing fields

    1. Test case: `edit 1`<br>
       Expected: No person is edited. An error message is shown.


### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Clearing all persons

1. Clearing the address book

   1. Prerequisites: List all persons using the `list` command. There are multiple persons in the address book.

   2. Test case: `clear`<br>
      Expected: All persons are removed from the list. A success message is shown.

2. Clearing an already empty address book

   1. Prerequisites: The address book is empty.

   2. Test case: `clear`<br>
      Expected: The list remains empty. A success message is shown.

### Help command

1. Opening the help window

    1. Test case: `help`<br>
       Expected: The help message appears.

2. Help with extra parameters

    1. Test case: `help abc`<br>
       Expected: The help message appears.

### Command history navigation

1. Navigating to previous commands

   1. Prerequisites: Enter several commands such as `list`, `find John`, and `help`.

   2. Press the Up arrow key once.<br>
      Expected: The most recently entered command is shown in the command box.

   3. Press the Up arrow key again.<br>
      Expected: The next earlier command is shown in the command box.

2. Navigating to newer commands

   1. Prerequisites: Use the Up arrow key at least once to move into command history.

   2. Press the Down arrow key once.<br>
      Expected: A more recent command is shown in the command box.

3. Navigating when there is no command history

   1. Prerequisites: Fresh launch of the app without entering any command.

   2. Press the Up arrow key.<br>
      Expected: No command is shown. The command box remains empty.

4. Navigating beyond the oldest command

   1. Prerequisites: There are previously entered commands.

   2. Repeatedly press the Up arrow key until the oldest command is shown.

   3. Press the Up arrow key again.<br>
      Expected: The oldest command remains shown.

5. Navigating beyond the most recent command

   1. Prerequisites: Use the Up arrow key to navigate through command history.

   2. Repeatedly press the Down arrow key until the most recent position is reached.

   3. Press the Down arrow key again.<br>
      Expected: The command box becomes empty.

### Saving data

1. Saving data after add/edit/delete/clear

   1. Perform an `add`, `edit`, `delete`, or `clear` command.

   2. Close the application.

   3. Re-launch the application.<br>
      Expected: The changes made previously are still present.

2. Dealing with missing data file

   1. Prerequisites: The data file `[JAR file location]/data/addressbook.json` does not exist.

   2. Launch the application.<br>
      Expected: The application starts successfully with an empty list.
   
3. Dealing with corrupted data file

   1. Prerequisites: The data file `[JAR file location]/data/addressbook.json` contains invalid JSON.

   2. Launch the application.<br>
      Expected: The application loads an empty list.

